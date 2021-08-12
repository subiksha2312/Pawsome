package com.example.pawsome.data

import com.google.gson.annotations.SerializedName



data class DogBreedInfo(
    @SerializedName("weight") val weight : DogWeight,
    @SerializedName("height") val height : DogHeight,
    @SerializedName("id") val id : Int,
    @SerializedName("name") val name : String,
    @SerializedName("bred_for") val bred_for : String,
    @SerializedName("breed_group") val breed_group : String,
    @SerializedName("life_span") val life_span : String,
    @SerializedName("temperament") val temperament : String,
    @SerializedName("reference_image_id") val reference_image_id : String
)


data class DogWeight(
    @SerializedName("imperial") val imperial : String,
    @SerializedName("metric") val metric : String
)


data class DogHeight(
    @SerializedName("imperial") val imperial : String,
    @SerializedName("metric") val metric : String
)


data class DogImage(
    @SerializedName("breeds") val breeds : List<DogBreedInfo>,
    @SerializedName("id") val id : String,
    @SerializedName("url") val url : String,
    @SerializedName("width") val width : Int,
    @SerializedName("height") val height : Int
)

data class DogFaves(
    @SerializedName("faveid") val faveid : String,
    @SerializedName("message") val message : String
        )

data class deleteDogFaves(
    @SerializedName("message") val message : String
)



