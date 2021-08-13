package com.example.pawsome.data

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.pawsome.api.DogDao


@Database (entities = arrayOf(DogTable::class), version = 1)

public abstract class DogDatabase : RoomDatabase() {
    abstract fun dogDao() : DogDao

    companion object {

        @Volatile
        private var INSTANCE: DogDatabase? = null

        fun getDatabase(context: Context) : DogDatabase {
            Log.d("database1","creating database")
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DogDatabase::class.java,
                    "dog_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}