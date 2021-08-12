package com.example.pawsome.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.example.pawsome.R
import com.squareup.picasso.Picasso

class DogImageDetail : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dog_image_detail)

        val dName=intent.getStringExtra("dogName")
        val dtemperament=intent.getStringExtra("temperament")
        val dBreedFor=intent.getStringExtra("breedFor")
        val dHeight=intent.getStringExtra("dHeight")
        val dWeight=intent.getStringExtra("dWeight")
        val dLifespan=intent.getStringExtra("lifespan")
        val dPhoto=intent.getStringExtra("dogPhoto")

        var dogImageA=findViewById<ImageView>(R.id.dogImage)
        Picasso.get().load(dPhoto).into(dogImageA)

        var nameText=(findViewById(R.id.dogName) as TextView)
        nameText.setText(" Name: $dName")

        var temperamentText=(findViewById(R.id.temperament) as TextView)
        temperamentText.setText(" Temperament: $dtemperament")

        var breedText=(findViewById(R.id.breedFor) as TextView)
        breedText.setText(" Breed: $dBreedFor")

        var heightText=(findViewById(R.id.dHeight) as TextView)
        heightText.setText(" Height: $dHeight")

        var weightText=(findViewById(R.id.dWeight) as TextView)
        weightText.setText(" Weight: $dWeight")

        var lifespanText=(findViewById(R.id.lifespan) as TextView)
        lifespanText.setText(" Lifespan: $dLifespan")

    }





}