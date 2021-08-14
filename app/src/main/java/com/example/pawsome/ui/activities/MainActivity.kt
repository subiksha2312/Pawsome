package com.example.pawsome.ui.activities

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pawsome.R
import com.example.pawsome.api.DogImageRetriever
import com.example.pawsome.application.DogApp
import com.example.pawsome.data.DogBreed
import com.example.pawsome.data.DogBreedInfo
import com.example.pawsome.ui.adapter.DogBreedsAdapter
import com.example.pawsome.ui.adapter.DogInfoAdapter
import com.example.pawsome.viewmodel.DogViewModel
import com.example.pawsome.viewmodel.DogViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_dog_image_detail.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dog_info.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.provider.MediaStore

import android.graphics.BitmapFactory
import android.net.Uri
import kotlinx.android.synthetic.main.activity_dog_image_detail.dogImage
import kotlinx.android.synthetic.main.activity_favourites_section.*
import kotlinx.android.synthetic.main.dog_info.*
import java.io.ByteArrayOutputStream


class MainActivity : AppCompatActivity() {

    private lateinit var faveButton: ImageButton
    private lateinit var uploadButton:ImageButton
    private lateinit var searchView: EditText
    private lateinit var searchButton: ImageButton
    private var mResultList: List<DogBreed> = emptyList()
    private var mSearchedList :List<DogBreedInfo> = emptyList()
    private val dogRetriever = DogImageRetriever()

    private val dogViewModel : DogViewModel by viewModels {
        DogViewModelFactory((application as DogApp).repository)
    }

    private val callbackSearch = object:Callback<List<DogBreedInfo>> {
        override fun onFailure(call: Call<List<DogBreedInfo>>, t: Throwable) {
            Log.e("MainActivitySearch","${t.message}")
        }

        override fun onResponse(
            call: Call<List<DogBreedInfo>>,
            response: Response<List<DogBreedInfo>>
        ) {
            response?.isSuccessful.let {
                mSearchedList = response?.body() ?: emptyList()
                Log.d("mainActivitySearch","${mSearchedList}")
                dogInfoList.adapter = DogBreedsAdapter(mSearchedList)
            }

        }
    }

    private val callback = object: Callback<List<DogBreed>> {
        override fun onFailure(call: Call<List<DogBreed>>, t: Throwable) {
            Log.e("MainActivity","${t.message}")
        }

        override fun onResponse(call: Call<List<DogBreed>>, response: Response<List<DogBreed>>) {
            response?.isSuccessful.let {
                mResultList = response?.body() ?: emptyList()
                Log.d("mainActivity","$mResultList")
                val onItemClicked ={position: Int -> onCardItemClick(position)}
                dogInfoList.adapter = DogInfoAdapter(mResultList,onItemClicked,dogViewModel,this@MainActivity)
            }
        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val builder: StrictMode.VmPolicy.Builder =StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())

        val policy : StrictMode.ThreadPolicy  =  StrictMode.ThreadPolicy.Builder().permitAll().build();
       StrictMode.setThreadPolicy(policy)

        setContentView(R.layout.activity_main)



        dogInfoList.layoutManager = LinearLayoutManager(this)

        if (isNetworkConnected()) {
            dogRetriever.getDogImages(callback)
        } else {
            AlertDialog.Builder(this).setTitle("No internet connection")
                .setMessage("check your connectivity")
                .setPositiveButton(android.R.string.ok) { _, _ -> }
                .setIcon(android.R.drawable.ic_dialog_alert).show()
        }

        faveButton = findViewById(R.id.faveButton)

        faveButton.setOnClickListener {
        val faveintent = Intent(this, FavouritesSection::class.java)
        startActivity(faveintent)
    }

        uploadButton=findViewById(R.id.uploadButton)

        uploadButton.setOnClickListener{
            val uploadintent = Intent(this, UploadSection::class.java)
            startActivity(uploadintent)
        }



        searchView=findViewById(R.id.search)
        searchButton=findViewById(R.id.searchButton)


        searchButton.setOnClickListener(){
            val searchedName= (searchView.text).toString()
            if(searchedName.isEmpty()) {
                Toast.makeText(this,"Invalid search query",Toast.LENGTH_SHORT).show()
            }
            else {
                if (isNetworkConnected()) {
                    dogRetriever.searchDogBreeds(searchedName,callbackSearch)
                } else {
                    AlertDialog.Builder(this).setTitle("No internet connection")
                        .setMessage("check your connectivity")
                        .setPositiveButton(android.R.string.ok) { _, _ -> }
                        .setIcon(android.R.drawable.ic_dialog_alert).show()
                }
            }
        }

    }





    private fun onCardItemClick(position: Int) {
        if(mResultList[position].name.isNotEmpty()) {
            val intent= Intent(this,DogImageDetail::class.java)
            intent.putExtra("dogName", mResultList[position].name)
            intent.putExtra("temperament","${mResultList[position].temperament}")
            intent.putExtra("breedFor","${mResultList[position].bredFor}")
            intent.putExtra("dHeight","${mResultList[position].height}")
            intent.putExtra("dWeight","${mResultList[position].weight}")
            intent.putExtra("lifespan","${mResultList[position].lifeSpan}")
            intent.putExtra("dogPhoto","${mResultList[position].image.url}")
            startActivity(intent)
           //Toast.makeText(this,mResultList[position].breeds[0].name,Toast.LENGTH_SHORT).show()
        }
        else {
            Toast.makeText(this,"breed info not available",Toast.LENGTH_SHORT).show()
        }
    }

    fun isNetworkConnected() :Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork =  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            connectivityManager.activeNetwork
        } else {
            TODO("VERSION.SDK_INT < M")
        }

        val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)
        return networkCapabilities != null && networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
}