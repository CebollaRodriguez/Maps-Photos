package com.example.mapsphotos.ui.coordinates

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.mapsphotos.R
import com.example.mapsphotos.data.model.uiModel
import com.example.mapsphotos.databinding.ActivityCoordinatesBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CoordinatesActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var binding: ActivityCoordinatesBinding
    private lateinit var maps: GoogleMap
    private val coordinatesViewModel: CoordinatesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCoordinatesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        createMapInstance()
        coordinatesViewModel.getCurrentPosition(this)
    }

    private fun createMapInstance() {
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        maps = googleMap

        coordinatesViewModel.ui.observe(this, Observer {
            updateUi(it)
        })

        coordinatesViewModel.onMapLoaded()
        maps.uiSettings.isZoomControlsEnabled = true
    }

    @SuppressLint("MissingPermission")
    private fun updateUi(uiModel: uiModel) {
        if (uiModel.currentLocation != null && uiModel.currentLocation != maps.cameraPosition.target) {
            maps.isMyLocationEnabled = true
            maps.animateCamera(CameraUpdateFactory.newLatLngZoom(uiModel.currentLocation, 14f))
            binding.tvCoordinates.text = " ${uiModel.currentLocation.latitude} , ${uiModel.currentLocation.longitude}"
        }

    }
}