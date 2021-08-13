package com.example.pawsome.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dog_table")
data class DogTable (
    @PrimaryKey @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "name") val name : String,
    @ColumnInfo(name = "weight") val dogWeight : String,
    @ColumnInfo(name = "height") val dogHeight : String,
    @ColumnInfo(name = "breed_group") val breedGroup : String,
    @ColumnInfo(name = "bred_for") val bredFor : String,
    @ColumnInfo(name = "life_span") val lifeSpan : String,
    @ColumnInfo(name = "temperament") val temperament : String
)
