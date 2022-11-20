package com.example.mapsphotos.data.model

import com.google.android.gms.maps.model.LatLng

data class uiModel(
    val currentLocation: LatLng?,
    val formattedLatLng: String
) {

    companion object {
        val Empty = uiModel(
            currentLocation = null,
            formattedLatLng = ""
        )
    }
}