package com.example.mapsphotos.data

import com.example.mapsphotos.data.db.dao.ImgDao
import com.example.mapsphotos.domain.model.ImageDomain
import com.example.mapsphotos.domain.model.toDatabase
import com.example.mapsphotos.domain.model.toDomain
import javax.inject.Inject

class ImgRepository @Inject constructor(
    private val imgDao: ImgDao
) {

    suspend fun getImgFromDb(): ImageDomain? {
        val response = imgDao.getImg()
        return response?.toDomain()
    }

    suspend fun insertImg(imageDomain: ImageDomain) {
        imgDao.insertImage(imageDomain.toDatabase())
    }

    suspend fun deleteImg() {
        imgDao.deleteImg()
    }
}