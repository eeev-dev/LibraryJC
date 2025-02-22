package com.example.library.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [PlaceEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract val dao : PlaceDao
    companion object {
        fun createDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "database.db"
            ).build()
        }
    }
}