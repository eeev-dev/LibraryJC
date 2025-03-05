package com.example.library.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.library.App
import com.example.library.data.local.AppDatabase
import com.example.library.data.local.PlaceEntity
import com.example.library.data.local.toPlace
import com.example.library.data.model.Place
import kotlinx.coroutines.launch

class DetailsViewModel(val database: AppDatabase) : ViewModel() {

    var place: Place? = null

    fun getPlace(id: Int) = viewModelScope.launch {
        place = database.dao.getItem(id)?.toPlace()
    }

    fun toLike(likedPlace: PlaceEntity) = viewModelScope.launch {
        database.dao.insertItem(likedPlace)
    }

    @Suppress("UNCHECKED_CAST")
    companion object {
        val factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val database =
                    (checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]) as App).database
                return DetailsViewModel(database) as T
            }
        }
    }
}