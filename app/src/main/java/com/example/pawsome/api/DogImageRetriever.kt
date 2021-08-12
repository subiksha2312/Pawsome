package com.example.pawsome.api

import com.example.pawsome.data.DogImage
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import android.R
import com.example.pawsome.data.DogBreedInfo


class DogImageRetriever {

    private var service: DogAPIService

    companion object {
       // const val BASE_URL = "https://api.github.com"
        const val BASE_URL = "https://api.thedogapi.com"
    }

    init {
        val retrofit = Retrofit.Builder()

            .baseUrl(BASE_URL)
            .addConverterFactory((GsonConverterFactory.create()))
            .build()

        service = retrofit.create(DogAPIService::class.java)
    }

    fun getDogImages(callback: Callback<List<DogImage>>) {
        val call = service.retrieveDogImages()
        call.enqueue(callback)


    }

    fun searchDogBreeds(searchQuery: String, callback: Callback<List<DogBreedInfo>>) {
        val callSearched = service.searchDogBreeds(searchQuery)
        callSearched.enqueue(callback)

    }


}
