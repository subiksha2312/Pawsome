package com.example.pawsome.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import android.widget.ImageView
import com.example.pawsome.R


class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val backgroundtag:ImageView = findViewById(R.id.logo)
        val slide = AnimationUtils.loadAnimation(this, R.anim.slide)
        backgroundtag.startAnimation(slide)

        Handler().postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        },3000)
    }
}


