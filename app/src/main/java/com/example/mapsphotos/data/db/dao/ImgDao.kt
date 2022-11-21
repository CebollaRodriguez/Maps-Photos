package com.example.mapsphotos.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mapsphotos.data.db.entities.ImageEntity

@Dao
interface ImgDao {

    @Query("SELECT * FROM img_table")
    suspend fun getImg(): ImageEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertImage(imgEntity: ImageEntity)

    @Query("Delete From img_table")
    suspend fun deleteImg()
}