package com.example.pawsome.api

import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import android.R
import android.content.Context
import android.net.Uri
import android.util.Log
import com.example.pawsome.data.DogBreed
import com.example.pawsome.data.DogBreedInfo
import com.example.pawsome.data.DogImageUploadResponse
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.logging.HttpLoggingInterceptor
import java.io.File


class DogImageRetriever {

    private var service: DogAPIService


    companion object {
       // const val BASE_URL = "https://api.github.com"
        const val BASE_URL = "https://api.thedogapi.com"
    }

    init {


        val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }

        val client : OkHttpClient = OkHttpClient.Builder().apply {
            this.addInterceptor(interceptor)
        }.build()

        val retrofit = Retrofit.Builder()

            .baseUrl(BASE_URL)
            .addConverterFactory((GsonConverterFactory.create()))
            .client(client)
            .build()

        service = retrofit.create(DogAPIService::class.java)
    }

    fun getDogImages(callback: Callback<List<DogBreed>>) {
        val call = service.retrieveDogImages()
        call.enqueue(callback)


    }

    fun searchDogBreeds(searchQuery: String, callback: Callback<List<DogBreedInfo>>) {
        val callSearched = service.searchDogBreeds(searchQuery)
        callSearched.enqueue(callback)

    }

    fun uploadDogImage(filePath: File, fileUri : Uri, context: Context, callback : Callback<DogImageUploadResponse>) {

        val requestFile : RequestBody =
            RequestBody.create(
                "application/octet-stream".toMediaTypeOrNull(),
                filePath)


        val requestBody : MultipartBody.Part =
            MultipartBody.Part.createFormData("file", filePath.path, requestFile)


        val descriptionString : String = ""

        val file : RequestBody = RequestBody.create(MultipartBody.FORM, descriptionString)
        Log.d("upload5", "${filePath.path}")

        val call = service.uploadDogImage(file, requestBody)
        call.enqueue(callback)


    }


}
