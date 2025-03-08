package com.example.library.data.local.place

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PlaceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(placeEntity: PlaceEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItems(items: List<PlaceEntity>)

    @Delete
    suspend fun deleteItem(placeEntity: PlaceEntity)

    @Query("SELECT * FROM places")
    fun getAllItems(): Flow<List<PlaceEntity>>

    @Query("SELECT * FROM places WHERE id = :id")
    suspend fun getItem(id: Int): PlaceEntity?
}