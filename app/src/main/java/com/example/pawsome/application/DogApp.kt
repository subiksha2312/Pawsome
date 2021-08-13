package com.example.pawsome.application

import android.app.Application
import com.example.pawsome.data.DogDatabase
import com.example.pawsome.data.DogRepository

class DogApp: Application(){

    val database by lazy {DogDatabase.getDatabase(this)}
    val repository by lazy {DogRepository(database.dogDao())}
}