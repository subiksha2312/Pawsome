package com.example.pawsome.data

import androidx.annotation.WorkerThread
import com.example.pawsome.api.DogDao
import kotlinx.coroutines.flow.Flow

class DogRepository(private val dogDao: DogDao) {

    val allDogs : Flow<List<DogTable>> = dogDao.getAllFavs()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(dogTable : DogTable) {
        dogDao.insert(dogTable)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteDog(id : Long) {
        dogDao.delete(id)
    }

}