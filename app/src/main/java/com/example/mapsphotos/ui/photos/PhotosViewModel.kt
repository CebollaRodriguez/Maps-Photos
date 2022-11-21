package com.example.mapsphotos.ui.photos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mapsphotos.domain.GetImageFromDatabaseUseCase
import com.example.mapsphotos.domain.model.ImageDomain
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotosViewModel @Inject constructor(
    private val getImageFromDatabaseUseCase: GetImageFromDatabaseUseCase
) : ViewModel() {
    private val _img = MutableLiveData<ImageDomain>()
    val img: LiveData<ImageDomain> = _img
    val isLoading = MutableLiveData<Boolean>()

    suspend fun getImgFromDb() {
        viewModelScope.launch {
            isLoading.postValue(true)
            val result = getImageFromDatabaseUseCase.getImageFromDb()
            if (result != null) {
                _img.postValue(result!!)
            }
        }
    }

    fun insertImg(imageDomain: ImageDomain) {
        viewModelScope.launch {
            //deleting the old picture, then save the new picture
            getImageFromDatabaseUseCase.deleteImg()
            getImageFromDatabaseUseCase.insertImageToDb(imageDomain)
        }
    }
}