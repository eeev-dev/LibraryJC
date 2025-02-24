package com.example.library.data.model

data class Place(
    val id: Int,
    val place: String,
    val city: String,
    val country: String,
    val rating: Double,
    var isFavorite: Boolean,
    val price: Double,
    val duration: Int,
    val temperature: Int,
    val description: String,
    val imageLink: String
)