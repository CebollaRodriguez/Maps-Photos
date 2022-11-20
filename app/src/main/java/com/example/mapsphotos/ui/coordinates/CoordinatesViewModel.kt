package com.example.mapsphotos.ui.coordinates

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.mapsphotos.R
import com.example.mapsphotos.data.model.uiModel
import com.example.mapsphotos.domain.GetPermissionsUseCase
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CoordinatesViewModel @Inject constructor(
    private val getPermissionsUseCase: GetPermissionsUseCase
) : ViewModel() {

    private val _ui = MutableLiveData(uiModel.Empty)
    val ui: LiveData<uiModel> = _ui
    val liveLocation = MutableLiveData<LatLng>()

    fun getCurrentPosition(activity: AppCompatActivity) {

        getPermissionsUseCase.getLocationPermissionProvider(activity)

        getPermissionsUseCase.liveLocation.observe(activity, Observer {
            val current = _ui.value
            //val formattedLatLng = activity.getString(R.string.coordinates, it)
            _ui.value =current?.copy(currentLocation = it)
        })
    }

    fun onMapLoaded() {
        getPermissionsUseCase.requestUserLocation()
    }
}