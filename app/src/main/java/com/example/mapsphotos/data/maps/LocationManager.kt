package com.example.mapsphotos.data.maps

import android.annotation.SuppressLint
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.LatLng
import javax.inject.Inject

class LocationManager @Inject constructor() {

    val liveLocation = MutableLiveData<LatLng>()

    @SuppressLint("MissingPermission")
    fun getUserLocation(activity: AppCompatActivity) {
        //making the client for to get the last location
        val client by lazy { LocationServices.getFusedLocationProviderClient(activity) }

        client.lastLocation.addOnSuccessListener { location ->
            val latLng = LatLng(location.latitude, location.longitude)
            liveLocation.postValue(latLng)
        }

        updateLocation(client)
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(result: LocationResult) {
            //getting the current location
            val currentLocation = result.lastLocation
            val latLng = LatLng(currentLocation!!.latitude, currentLocation.longitude)
            liveLocation.postValue(latLng)
        }
    }

    @SuppressLint("MissingPermission")
    fun updateLocation(client: FusedLocationProviderClient) {
        val locationRequest = LocationRequest.create()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 1000

        client.requestLocationUpdates(
            locationRequest, locationCallback,
            Looper.getMainLooper()
        )
    }
}