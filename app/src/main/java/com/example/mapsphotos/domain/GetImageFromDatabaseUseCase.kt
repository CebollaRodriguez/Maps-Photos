package com.example.mapsphotos.domain

import com.example.mapsphotos.data.ImgRepository
import com.example.mapsphotos.domain.model.ImageDomain
import javax.inject.Inject

class GetImageFromDatabaseUseCase @Inject constructor(
    private val imgRepository: ImgRepository
) {

    suspend fun insertImageToDb(imageDomain: ImageDomain){
        imgRepository.insertImg(imageDomain)
    }

    suspend fun getImageFromDb(): ImageDomain? = imgRepository.getImgFromDb()

    suspend fun deleteImg() {
        imgRepository.deleteImg()
    }
}