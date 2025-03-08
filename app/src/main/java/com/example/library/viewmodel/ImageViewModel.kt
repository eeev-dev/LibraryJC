package com.example.library.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.library.App
import com.example.library.data.local.AppDatabase
import com.example.library.data.local.image.ImageEntity
import com.example.library.data.local.image.toUnsplash
import com.example.library.data.model.UnsplashImage
import kotlinx.coroutines.launch

class ImageViewModel(val database: AppDatabase) : ViewModel() {

    var image: UnsplashImage? = null

    fun getImage(id: String) = viewModelScope.launch {
        image = database.imageDao.getItem(id)?.toUnsplash()
    }

    fun toLike(likedImage: ImageEntity) = viewModelScope.launch {
        database.imageDao.insertItem(likedImage)
    }

    fun toRate(ratedImage: ImageEntity) = viewModelScope.launch {
        database.imageDao.insertItem(ratedImage)
    }

    @Suppress("UNCHECKED_CAST")
    companion object {
        val factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val database =
                    (checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]) as App).database
                return ImageViewModel(database) as T
            }
        }
    }
}