package com.example.mapsphotos.domain

import android.Manifest
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
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
        locationPermissionProvider.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
    }
}