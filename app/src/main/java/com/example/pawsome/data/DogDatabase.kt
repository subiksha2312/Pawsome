package com.example.pawsome.data

import android.content.Context
import android.util.Log
import com.example.pawsome.api.DogDao
import android.graphics.BitmapFactory

import android.graphics.Bitmap
import android.util.Base64
import androidx.room.*
import java.io.ByteArrayOutputStream
import java.lang.Exception





@Database (entities = arrayOf(DogTable::class), version = 2)
@TypeConverters(ImageBitmapString::class)

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
                    "dog_database2"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}

 class ImageBitmapString {

     fun ImageBitmapString() {

     }

     @TypeConverter
     fun BitMapToString(bitmap: Bitmap): String? {
         val baos = ByteArrayOutputStream()
         bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
         val b: ByteArray = baos.toByteArray()
         val temp: String = Base64.encodeToString(b, Base64.DEFAULT)
         return if (temp == null) {
             null
         } else temp
     }

     @TypeConverter
     fun StringToBitMap(encodedString: String?): Bitmap? {
         return try {
             val encodeByte: ByteArray = Base64.decode(encodedString, Base64.DEFAULT)
             val bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size)
             bitmap
         } catch (e: Exception) {
             e.message
             null
         }
     }

 }