package com.example.photogalleryapp

import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class FullscreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fullscreen)

        val imageResId = intent.getIntExtra("imageResId", R.mipmap.ic_launcher)
        val fullscreenImage = findViewById<ImageView>(R.id.fullscreenImage)
        val backButton = findViewById<ImageButton>(R.id.backButton)

        fullscreenImage.setImageResource(imageResId)

        backButton.setOnClickListener {
            finish()
        }
    }
}