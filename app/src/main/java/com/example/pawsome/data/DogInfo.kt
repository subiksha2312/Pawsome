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

/*
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

 */

data class ImageData (
    val id: String,
    val width: Long,
    val height: Long,
    val url: String
)

data class DogBreed (
    val weight: DogWeight,
    val height: DogHeight,
    val id: Long,
    val name: String,
    val bredFor: String,
    val breedGroup: String,
    val lifeSpan: String,
    val temperament: String,
    val origin: String,
    val referenceImageID: String,
    val image: ImageData
)

data class DogImageUploadResponse (
    @SerializedName("id") val id : String,
    @SerializedName("url") val url : String,
    @SerializedName("width") val width : Int,
    @SerializedName("height") val height : Int,
    @SerializedName("original_filename") val original_filename : String,
    @SerializedName("pending") val pending : Int,
    @SerializedName("approved") val approved : Int

)

