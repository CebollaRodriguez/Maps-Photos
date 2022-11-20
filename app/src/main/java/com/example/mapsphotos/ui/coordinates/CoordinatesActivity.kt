package com.example.mapsphotos.ui.coordinates

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mapsphotos.databinding.ActivityCoordinatesBinding

class CoordinatesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCoordinatesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCoordinatesBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}