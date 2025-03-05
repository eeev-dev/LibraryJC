package com.example.library.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.library.App
import com.example.library.BuildConfig
import com.example.library.data.local.AppDatabase
import com.example.library.data.model.UnsplashImage
import com.example.library.data.network.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ImagesViewModel(val database: AppDatabase) : ViewModel() {
    private val _images = MutableStateFlow<List<UnsplashImage>>(emptyList())
    val images: StateFlow<List<UnsplashImage>> = _images

    init {
        fetchImages()
    }

    private fun fetchImages() {
        viewModelScope.launch {
            try {
                _images.value = RetrofitInstance.imageApi.getImages(
                    apiKey = BuildConfig.UNSPLASH_API_KEY,
                    query = "nature"
                ).results
            } catch (e: Exception) {
                Log.e("ImageViewModel", "Ошибка при загрузке данных", e)
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    companion object {
        val factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val database =
                    (checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]) as App).database
                return ImagesViewModel(database) as T
            }
        }
    }
}