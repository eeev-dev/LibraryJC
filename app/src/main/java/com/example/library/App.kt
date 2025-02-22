package com.example.library

import android.app.Application
import com.example.library.data.local.AppDatabase

class App : Application() {
    val database by lazy { AppDatabase.createDatabase(this) }
}