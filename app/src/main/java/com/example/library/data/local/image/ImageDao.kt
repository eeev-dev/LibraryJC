package com.example.library.data.local.image

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ImageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(imageEntity: ImageEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItems(items: List<ImageEntity>)

    @Delete
    suspend fun deleteItem(imageEntity: ImageEntity)

    @Query("SELECT * FROM images ORDER BY id ASC")
    fun getAllItems(): Flow<List<ImageEntity>>

    @Query("SELECT * FROM images WHERE id = :id")
    suspend fun getItem(id: String): ImageEntity?
}