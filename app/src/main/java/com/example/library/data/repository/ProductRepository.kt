package com.example.library.data.repository

import com.example.library.data.model.Place
import com.example.library.data.network.RetrofitInstance

class ProductRepository {
    suspend fun getPlaces(): List<Place> {
        val products = RetrofitInstance.productApi.getProducts().products

        return products.map { product ->
            Place(
                id = product.id,
                place = product.title,
                city = if (product.brand != null) product.brand else "City",
                country = product.category,
                rating = product.rating,
                isFavorite = false, 
                price = product.price,
                duration = product.stock,
                temperature = product.weight,
                description = product.description,
                imageLink = product.images.firstOrNull() ?: "" // Берём первую картинку
            )
        }
    }
}