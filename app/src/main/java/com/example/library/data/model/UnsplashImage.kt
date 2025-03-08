package com.example.library.data.model

import kotlinx.serialization.Serializable

@Serializable
data class UnsplashImage(
    val id: String,
    val description: String?,
    val urls: ImageUrls,
    val user: User?,
    val rating: Int = 0,
    val isFavorite: Boolean = false
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