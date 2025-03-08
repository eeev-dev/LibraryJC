package com.example.library.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.library.data.local.image.Converters
import com.example.library.data.local.image.ImageDao
import com.example.library.data.local.image.ImageEntity
import com.example.library.data.local.place.PlaceDao
import com.example.library.data.local.place.PlaceEntity

@Database(entities = [PlaceEntity::class, ImageEntity::class], version = 2)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract val placeDao : PlaceDao
    abstract val imageDao : ImageDao
    companion object {
        fun createDatabase(context: Context): AppDatabase {
            val MIGRATION_1_2 = object : Migration(1, 2) {
                override fun migrate(database: SupportSQLiteDatabase) {
                    database.execSQL(
                        "CREATE TABLE IF NOT EXISTS images (" +
                                "id TEXT PRIMARY KEY NOT NULL," +
                                "description TEXT," +
                                "urls TEXT NOT NULL," +
                                "user TEXT," +
                                "rating INTEGER NOT NULL DEFAULT 0," +
                                "isFavorite INTEGER NOT NULL DEFAULT 0)"
                    )
                }
            }
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "database.db")
                .addMigrations(MIGRATION_1_2)
                .build()
        }
    }
}