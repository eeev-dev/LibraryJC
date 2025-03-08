package com.example.library.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.library.App
import com.example.library.data.local.AppDatabase
import com.example.library.data.local.image.ImageEntity
import com.example.library.data.local.image.toEntity
import com.example.library.data.local.image.toUnsplash
import com.example.library.data.model.UnsplashImage
import com.example.library.data.repository.ImagesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class ImagesViewModel(val database: AppDatabase) : ViewModel() {
    private val _images = MutableStateFlow<List<UnsplashImage>>(emptyList())
    val images: StateFlow<List<UnsplashImage>> = _images

    fun toLike(likedImage: ImageEntity) = viewModelScope.launch {
        database.imageDao.insertItem(likedImage)
    }

    init {
        viewModelScope.launch {
            val imagesList = database.imageDao.getAllItems()
            try {
                imagesList
                    .catch { e -> Log.e("ImagesViewModel", "Ошибка при загрузке данных из Room", e) }
                    .distinctUntilChanged()
                    .collect { itemsList ->
                        if (itemsList.isEmpty()) {
                            fetchImages()
                        }
                        else {
                            Log.e("ImagesViewModel", "Беру данные из Room")
                            _images.value = itemsList.map { it.toUnsplash() }
                        }
                    }
            } catch (e: Exception) {
                Log.e("ImagesViewModel", "Ошибка в collect", e)
            }
        }
    }

    private fun fetchImages() {
        viewModelScope.launch {
            try {
                _images.value = ImagesRepository().getImages()
                database.imageDao.insertItems(_images.value.map { it.toEntity() })
            } catch (e: Exception) {
                Log.e("ImagesViewModel", "Ошибка при загрузке данных", e)
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