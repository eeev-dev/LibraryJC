package com.example.library.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.library.App
import com.example.library.data.local.AppDatabase
import com.example.library.data.local.place.PlaceEntity
import com.example.library.data.local.place.toEntity
import com.example.library.data.local.place.toPlace
import com.example.library.data.model.Place
import com.example.library.data.repository.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class MainViewModel(val database: AppDatabase) : ViewModel() {
    private val _places = MutableStateFlow<List<Place>>(emptyList())
    val places: StateFlow<List<Place>> = _places

    val placesList = database.placeDao.getAllItems()

    fun toLike(likedPlace: PlaceEntity) = viewModelScope.launch {
        database.placeDao.insertItem(likedPlace)
    }

    init {
        viewModelScope.launch {
            try {
                placesList
                    .catch { e -> Log.e("PlaceViewModel", "Ошибка при загрузке данных из Room", e) }
                    .distinctUntilChanged()
                    .collect { itemsList ->
                    if (itemsList.isEmpty()) {
                        fetchPlaces()
                    }
                    else {
                        Log.e("PlaceViewModel", "Беру данные из Room")
                        _places.value = itemsList.map { it.toPlace() }
                    }
                }
            } catch (e: Exception) {
                Log.e("PlaceViewModel", "Ошибка в collect", e)
            }
        }
    }

    private fun fetchPlaces() {
        viewModelScope.launch {
            try {
                _places.value = ProductRepository().getPlaces()
                database.placeDao.insertItems(_places.value.map { it.toEntity() })
            } catch (e: Exception) {
                Log.e("PlaceViewModel", "Ошибка при загрузке данных", e)
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    companion object {
        val factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val database =
                    (checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]) as App).database
                return MainViewModel(database) as T
            }
        }
    }
}