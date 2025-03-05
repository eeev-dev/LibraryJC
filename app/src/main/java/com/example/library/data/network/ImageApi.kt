package com.example.library.data.network

import com.example.library.data.model.UnsplashImage
import kotlinx.serialization.Serializable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ImageApi {
    @GET("search/photos")
    suspend fun getImages(
        @Query("query") query: String,
        @Query("client_id") apiKey: String,
        @Query("per_page") perPage: Int = 30
    ): UnsplashResponse
}

data class UnsplashResponse(
    val results: List<UnsplashImage>
)