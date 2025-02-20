package com.example.library.data.network

import com.example.library.data.model.Product
import retrofit2.http.GET

interface ProductApi {
    @GET("products")
    suspend fun getProducts(): ProductResponse
}

data class ProductResponse(
    val products: List<Product>
)