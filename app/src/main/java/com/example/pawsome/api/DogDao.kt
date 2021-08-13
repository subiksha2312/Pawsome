package com.example.pawsome.api

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pawsome.data.DogTable
import kotlinx.coroutines.flow.Flow

@Dao
interface DogDao {
    @Query ("SELECT * FROM dog_table ORDER BY name ASC")
    fun getAllFavDogs() : Flow<List<DogTable>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(dogTable : DogTable)

    @Query("DELETE FROM dog_table WHERE id = :id")
    suspend fun delete(id : Int)
}