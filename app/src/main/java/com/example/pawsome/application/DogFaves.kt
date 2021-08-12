package com.example.pawsome.application

import android.app.Application
import com.example.pawsome.data.FaveDogDB
import com.example.pawsome.data.FaveDogs

class DogFaves: Application() {
    val database by lazy { FaveDogDB.getDatabase(this) }
    val repository by lazy { WordRepository(database.wordDao()) }
}








