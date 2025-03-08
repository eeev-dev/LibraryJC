package com.example.library.data.local.image

import com.example.library.data.model.UnsplashImage

fun UnsplashImage.toEntity(): ImageEntity {
    return ImageEntity(
        id = id,
        description = description,
        urls = urls,
        user = user,
        rating = rating,
        isFavorite = isFavorite
    )
}

fun ImageEntity.toUnsplash(): UnsplashImage {
    return UnsplashImage(
        id = id,
        description = description,
        urls = urls,
        user = user,
        rating = rating,
        isFavorite = isFavorite
    )
}