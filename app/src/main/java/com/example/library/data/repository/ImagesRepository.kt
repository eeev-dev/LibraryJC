package com.example.library.data.repository

import com.example.library.BuildConfig
import com.example.library.data.model.UnsplashImage
import com.example.library.data.network.RetrofitInstance

class ImagesRepository {
    suspend fun getImages(): List<UnsplashImage> {
        val images = RetrofitInstance.imageApi.getImages(
            apiKey = BuildConfig.UNSPLASH_API_KEY,
            query = "nature"
        ).results

        return images
    }
}