package com.example.pawsome.api

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.pawsome.data.FaveDogs

@Dao
interface FaveDogDao {
    @Query("SELECT * FROM FaveDogs")
    fun getAllFaveDogs(): List<FaveDogs>

    @Insert
    fun insertAll(vararg favedogs: FaveDogs)

    @Delete
    fun delete(favedogs : FaveDogs )
}