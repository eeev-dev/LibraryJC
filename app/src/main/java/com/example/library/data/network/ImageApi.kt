package com.example.library.data.network

import kotlinx.serialization.Serializable
import retrofit2.http.GET
import retrofit2.http.Query

interface ImageApi {
    @GET("search/photos")
    suspend fun getImages(
        @Query("query") query: String,
        @Query("client_id") apiKey: String,
        @Query("per_page") perPage: Int = 20
    ): UnsplashResponse
}

data class UnsplashResponse(
    val results: List<UnsplashImage>
)

@Serializable
data class UnsplashImage(
    val id: String,
    val description: String?,
    val tags: List<String>?,
    val urls: ImageUrls,
    val user: User?
)

@Serializable
data class ImageUrls(
    val regular: String?,
    val raw: String?
)

@Serializable
data class User(
    val id: String,
    val username: String?,
    val name: String?,
    val portfolio_url: String? // Портфолио автора
)