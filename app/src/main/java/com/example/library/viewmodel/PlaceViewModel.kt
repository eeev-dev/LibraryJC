package com.example.library.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.library.data.model.Place
import com.example.library.data.repository.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PlaceViewModel : ViewModel() {

    private val _places = MutableStateFlow<List<Place>>(emptyList())
    val places: StateFlow<List<Place>> = _places

    init {
        fetchPlaces()
    }

    private fun fetchPlaces() {
        viewModelScope.launch {
            try {
                // Запрос данных, например, из репозитория
                _places.value = ProductRepository().getPlaces()
            } catch (e: Exception) {
                Log.e("PlaceViewModel", "Ошибка при загрузке данных", e)
            }
        }
    }
}