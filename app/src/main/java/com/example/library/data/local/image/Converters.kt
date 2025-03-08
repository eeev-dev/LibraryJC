package com.example.library.data.local.image

import androidx.room.TypeConverter
import com.example.library.data.model.ImageUrls
import com.example.library.data.model.User
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun fromImageUrls(imageUrls: ImageUrls): String {
        return gson.toJson(imageUrls)
    }

    @TypeConverter
    fun toImageUrls(json: String): ImageUrls {
        return gson.fromJson(json, object : TypeToken<ImageUrls>() {}.type)
    }

    @TypeConverter
    fun fromUser(user: User): String {
        return gson.toJson(user)
    }

    @TypeConverter
    fun toUser(json: String): User {
        return gson.fromJson(json, object : TypeToken<User>() {}.type)
    }
}
