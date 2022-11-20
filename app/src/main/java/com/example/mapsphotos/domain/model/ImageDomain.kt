package com.example.mapsphotos.domain.model

import android.graphics.Bitmap
import com.example.mapsphotos.data.db.entities.ImageEntity

data class ImageDomain(
    val img: Bitmap?
)

fun ImageEntity.toDomain() = ImageDomain(
    img = img
)

fun ImageDomain.toDatabase() = ImageEntity(
    img = img
)