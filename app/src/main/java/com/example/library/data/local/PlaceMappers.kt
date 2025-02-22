package com.example.library.data.local

import com.example.library.data.model.Place

fun Place.toEntity(): PlaceEntity {
    return PlaceEntity(
        id = id,
        place = place,
        city = city,
        country = country,
        rating = rating,
        isFavorite = isFavorite,
        price = price,
        duration = duration,
        temperature = temperature,
        description = description,
        imageLink = imageLink
    )
}

fun PlaceEntity.toPlace(): Place {
    return Place(
        id = id,
        place = place,
        city = city,
        country = country,
        rating = rating,
        isFavorite = isFavorite,
        price = price,
        duration = duration,
        temperature = temperature,
        description = description,
        imageLink = imageLink
    )
}