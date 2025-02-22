package com.example.library.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.library.App
import com.example.library.data.local.AppDatabase
import com.example.library.data.local.toEntity
import com.example.library.data.local.toPlace
import com.example.library.data.model.Place
import com.example.library.data.repository.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class PlaceViewModel(val database: AppDatabase) : ViewModel() {
    val itemsList = database.dao.getAllItems()

    private val _places = MutableStateFlow<List<Place>>(emptyList())
    val places: StateFlow<List<Place>> = _places

    init {
        viewModelScope.launch {
            try {
                // Сбор данных из Flow
                itemsList
                    .catch { e -> Log.e("PlaceViewModel", "Ошибка при загрузке данных из Room", e) }
                    .distinctUntilChanged()
                    .collect { itemsList ->
                    // Если в Room нет данных, беру их с сервера
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
                // Запрос данных, например, из репозитория
                _places.value = ProductRepository().getPlaces()
                database.dao.insertItems(_places.value.map { it.toEntity() })
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
                return PlaceViewModel(database) as T
            }
        }
    }
}