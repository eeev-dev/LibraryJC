package com.example.library.data.repository

import android.util.Log
import com.example.library.data.model.Place
import com.example.library.data.network.RetrofitInstance
import kotlin.random.Random

class ProductRepository {
    suspend fun getPlaces(): List<Place> {
        val products = RetrofitInstance.api.getProducts().products

        // for(i in products.indices) Log.d("AAAAAAAAAAAAAAAAAAAAAAAA", i.toString() + " " + products[i].brand)

        return products.map { product ->
            Place(
                id = product.id,
                place = product.title,
                city = if (product.brand != null) product.brand else "City",
                country = product.category,
                rating = product.rating,
                isFavorite = Random.nextBoolean(), // Рандомное значение
                price = product.price,
                duration = product.stock,
                temperature = product.weight,
                description = product.description,
                imageLink = product.images.firstOrNull() ?: "" // Берём первую картинку
            )
        }
    }
}