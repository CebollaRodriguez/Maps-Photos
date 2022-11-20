package com.example.mapsphotos.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mapsphotos.R
import com.example.mapsphotos.databinding.ActivityNavigationBinding
import com.example.mapsphotos.ui.coordinates.CoordinatesActivity
import com.google.android.gms.maps.GoogleMap

class NavigationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNavigationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNavigationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUp()
        binding.lyPosition.setOnClickListener { goToCoordinatesScreen() }
    }

    private fun setUp() {
        title = "Principal"
    }

    private fun goToCoordinatesScreen() {
        val intent = Intent(this, CoordinatesActivity::class.java)
        startActivity(intent)
    }
}