package com.example.pawsome.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.cardview.widget.CardView
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pawsome.R
import com.example.pawsome.application.DogApp
import com.example.pawsome.data.DogTable
import com.example.pawsome.ui.adapter.DogFaveAdapter
import com.example.pawsome.viewmodel.DogViewModel
import com.example.pawsome.viewmodel.DogViewModelFactory
import kotlinx.android.synthetic.main.activity_favourites_section.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.dogInfoList

class FavouritesSection : AppCompatActivity() {



    private val dogViewModel : DogViewModel by viewModels {
        DogViewModelFactory((application as DogApp).repository)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favourites_section)



        val recyclerView =findViewById(R.id.dogFaveList) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)


       /* val dogImageFave = findViewById<ImageView>(R.id.dogImage)
        dogImageFave.setOnClickListener() {
            val intent: Intent = Intent(this, DogImageDetail::class.java)
            startActivity(intent)
        }

        */

        dogViewModel.allDogs.observe(this) {
            dogs ->
            run {
                Log.d("faves1","${dogs}")
                recyclerView.adapter = DogFaveAdapter(dogs,dogViewModel)
            }
        }

     }



}

