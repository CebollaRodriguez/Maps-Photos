package com.example.mapsphotos.data.maps

import android.annotation.SuppressLint
import android.app.Activity
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import javax.inject.Inject

class LocationManager @Inject constructor() {

    val liveLocation = MutableLiveData<LatLng>()

    @SuppressLint("MissingPermission")
    fun getUserLocation(activity: AppCompatActivity) {

        val client by lazy { LocationServices.getFusedLocationProviderClient(activity) }

        client.lastLocation.addOnSuccessListener {location ->
            val latLng = LatLng(location.latitude, location.longitude)
            liveLocation.postValue(latLng)
        }
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(result: LocationResult) {

            val currentLocation = result.lastLocation
            val latLng = LatLng(currentLocation!!.latitude, currentLocation.longitude)
            liveLocation.postValue(latLng)
        }
    }
}