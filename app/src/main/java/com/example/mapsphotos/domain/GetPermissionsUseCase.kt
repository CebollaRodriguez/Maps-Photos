package com.example.mapsphotos.domain

import android.Manifest
import android.app.Activity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.mapsphotos.data.maps.LocationManager
import com.google.android.gms.maps.model.LatLng
import javax.inject.Inject

class GetPermissionsUseCase @Inject constructor(
    private val locationManager: LocationManager
) {

    val liveLocation = MutableLiveData<LatLng>()

    private lateinit var locationPermissionProvider: ActivityResultLauncher<String>

    fun getLocationPermissionProvider(activity: AppCompatActivity) {
        locationPermissionProvider = activity.registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { granted ->
            if (granted) {
                locationManager.getUserLocation(activity)
                locationManager.liveLocation.observe(activity, Observer {
                    liveLocation.postValue(it)
                })
            }
        }
    }

    fun requestUserLocation() {
        locationPermissionProvider.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }
}