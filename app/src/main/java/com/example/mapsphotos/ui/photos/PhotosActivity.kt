package com.example.mapsphotos.ui.photos

import android.Manifest
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.graphics.drawable.toIcon
import androidx.core.net.toFile
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.example.mapsphotos.databinding.ActivityPhotosBinding
import com.example.mapsphotos.domain.model.ImageDomain
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PhotosActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPhotosBinding
    private val photosViewModel: PhotosViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPhotosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        photosViewModel.isLoading.observe(this, Observer {
            binding.btnTakePhoto.isVisible = it
        })
        setUp()

    }

    private fun setUp() {
        title = "Foto"
        getImageFromDb()
        binding.btnTakePhoto.setOnClickListener { getCameraPermission() }
    }

    private fun getImageFromDb() {
        CoroutineScope(Dispatchers.IO).launch {
            photosViewModel.getImgFromDb()

            runOnUiThread {
                photosViewModel.img.observe(this@PhotosActivity, Observer {
                    if (it.img != null) binding.ivIcon.setImageBitmap(it.img)

                })
            }
        }
    }

    private fun getCameraPermission() {

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            dispatchPictureIntent()
        } else {
            //Launching the request permission
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                2
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            2-> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                dispatchPictureIntent()
            }
        }
    }

    private fun dispatchPictureIntent() {
        //Searching an camera app and take the photo

        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePicture ->
            takePicture.resolveActivity(packageManager)?.also {
                startActivityForResult(takePicture, 1)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1 && resultCode == RESULT_OK) {
            //getting the photo and saving in the device
            data?.extras?.let {
                val imgBitmap = it.get("data") as Bitmap

                binding.ivIcon.setImageBitmap(imgBitmap)
                CoroutineScope(Dispatchers.IO).launch {
                    photosViewModel.deleteImg()
                    photosViewModel.insertImg(imageDomain = ImageDomain(imgBitmap))
                }
            }
        }
    }


}