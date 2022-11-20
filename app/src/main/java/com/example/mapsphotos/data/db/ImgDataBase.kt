package com.example.mapsphotos.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.mapsphotos.data.db.dao.ImgDao
import com.example.mapsphotos.data.db.entities.ImageEntity

@Database(entities = [ImageEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class ImgDataBase: RoomDatabase() {
    abstract fun getImgDao():ImgDao
}