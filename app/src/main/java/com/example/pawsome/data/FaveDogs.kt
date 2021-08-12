package com.example.pawsome.data

import android.content.Context
import androidx.room.*
import com.example.pawsome.api.FaveDogDao


@Entity
data class FaveDogs(
    @PrimaryKey val dogId: String,
    @ColumnInfo(name = "breedName") val breedName: String?,
    @ColumnInfo(name = "weight") val weight: String?,
    @ColumnInfo(name = "height") val height: String?,
    @ColumnInfo(name = "temperament") val temperament: String?,
    @ColumnInfo(name = "lifespan") val lifespan: String?
)

@Database(entities = arrayOf(FaveDogs::class), version = 1)
abstract class FaveDogDB: RoomDatabase() {
    abstract fun FaveDogDao() : FaveDogDao

    @Volatile
    private var INSTANCE: FaveDogDB?= null

    fun getDatabase(context:Context): FaveDogDB {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                FaveDogDB::class.java,
                "FaveDog_database"
            ).build()
            INSTANCE=instance
            instance
        }
    }
}


