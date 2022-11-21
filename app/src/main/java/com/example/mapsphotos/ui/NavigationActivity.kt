package com.example.mapsphotos.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mapsphotos.R
import com.example.mapsphotos.databinding.ActivityNavigationBinding
import com.example.mapsphotos.ui.coordinates.CoordinatesActivity
import com.example.mapsphotos.ui.photos.PhotosActivity

class NavigationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNavigationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNavigationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUp()
    }

    private fun setUp() {
        title = "Principal"
        binding.lyPosition.setOnClickListener { goToCoordinatesScreen() }
        binding.lyPhoto.setOnClickListener { goToPhotosScreen() }
    }

    private fun goToCoordinatesScreen() {
        val intent = Intent(this, CoordinatesActivity::class.java)
        startActivity(intent)
    }

    private fun goToPhotosScreen() {
        val intent = Intent(this, PhotosActivity::class.java)
        startActivity(intent)
    }
}