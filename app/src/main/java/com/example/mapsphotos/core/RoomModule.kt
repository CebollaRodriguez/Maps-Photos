package com.example.mapsphotos.core

import android.content.Context
import androidx.room.Room
import com.example.mapsphotos.data.db.ImgDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    private const val IMG_DB_NAME = "img_db"

    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext context: Context) = Room.databaseBuilder(
        context, ImgDataBase::class.java, IMG_DB_NAME
    ).build()

    @Singleton
    @Provides
    fun provideImgDao(db: ImgDataBase) = db.getImgDao()
}