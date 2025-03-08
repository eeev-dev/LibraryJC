package com.example.library.data.local.image

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.library.data.model.ImageUrls
import com.example.library.data.model.User

@Entity(tableName = "images")
data class ImageEntity(
    @PrimaryKey val id: String,
    val description: String?,
    val urls: ImageUrls,
    val user: User?,
    val rating: Int = 0,
    val isFavorite: Boolean = false
)