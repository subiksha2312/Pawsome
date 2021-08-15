package com.example.pawsome.api

import com.example.pawsome.data.DogBreed
import com.example.pawsome.data.DogBreedInfo
import com.example.pawsome.data.DogImageUploadResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface DogAPIService {

    @Headers("x-api-key:3d739c2f-28ed-40c5-8d67-e64b23642dd7")
    @GET("/v1/breeds")
    fun retrieveDogImages() : Call<List<DogBreed>>

    @Headers("x-api-key:3d739c2f-28ed-40c5-8d67-e64b23642dd7")
    @GET("/v1/breeds/search")
    fun searchDogBreeds(@Query("q")breedName: String) : Call<List<DogBreedInfo>>

    @Headers ("x-api-key:3d739c2f-28ed-40c5-8d67-e64b23642dd7")
    @Multipart
    @POST("v1/images/upload")
    fun uploadDogImage(
        @Part("file") description : RequestBody,
        @Part file : MultipartBody.Part
    ) : Call<DogImageUploadResponse>


/*
    @Headers("x-api-key:3d739c2f-28ed-40c5-8d67-e64b23642dd7")
    @POST("/v1/favourites")
    fun getFaves()

    @Headers("x-api-key:3d739c2f-28ed-40c5-8d67-e64b23642dd7")
    @POST("/v1/favourites/{favourite_id}")
    fun deleteFaves(@Query("favourite_id")id: Int) : Call<deleteDogFaves>

      @Headers ("x-api-key:3d739c2f-28ed-40c5-8d67-e64b23642dd7")
    @Multipart
    @POST("v1/images/upload")
    fun uploadDogImage(
        @Part("description") description : RequestBody,
        @Part file : MultipartBody.Part
    ) : Call<DogImageUploadResponse>



 */




    /*@Headers("x-api-key:3d739c2f-28ed-40c5-8d67-e64b23642dd7",
    "Content-Type: multipart/form-data")
    @POST("/v1/images/upload")
    fun uploadDogImages() :

     */


    //@GET("/search/repositories?q=language:kotlin&sort=stars&order=desc")


}