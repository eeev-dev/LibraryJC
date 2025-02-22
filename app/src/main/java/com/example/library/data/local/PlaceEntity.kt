package com.example.library.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "places")
data class PlaceEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val place: String,
    val city: String,
    val country: String,
    val rating: Double,
    val isFavorite: Boolean,
    val price: Double,
    val duration: Int,
    val temperature: Int,
    val description: String,
    val imageLink: String
)