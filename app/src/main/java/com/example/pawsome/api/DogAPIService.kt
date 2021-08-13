package com.example.pawsome.api

import com.example.pawsome.data.DogBreed
import com.example.pawsome.data.DogBreedInfo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface DogAPIService {

    @Headers("x-api-key:3d739c2f-28ed-40c5-8d67-e64b23642dd7")
    @GET("/v1/breeds")
    fun retrieveDogImages() : Call<List<DogBreed>>

    @Headers("x-api-key:3d739c2f-28ed-40c5-8d67-e64b23642dd7")
    @GET("/v1/breeds/search")
    fun searchDogBreeds(@Query("q")breedName: String) : Call<List<DogBreedInfo>>
/*
    @Headers("x-api-key:3d739c2f-28ed-40c5-8d67-e64b23642dd7")
    @POST("/v1/favourites")
    fun getFaves()

    @Headers("x-api-key:3d739c2f-28ed-40c5-8d67-e64b23642dd7")
    @POST("/v1/favourites/{favourite_id}")
    fun deleteFaves(@Query("favourite_id")id: Int) : Call<deleteDogFaves>



 */




    /*@Headers("x-api-key:3d739c2f-28ed-40c5-8d67-e64b23642dd7",
    "Content-Type: multipart/form-data")
    @POST("/v1/images/upload")
    fun uploadDogImages() :

     */


    //@GET("/search/repositories?q=language:kotlin&sort=stars&order=desc")


}