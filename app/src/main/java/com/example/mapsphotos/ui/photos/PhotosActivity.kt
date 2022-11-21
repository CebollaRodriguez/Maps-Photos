package com.example.mapsphotos.ui.photos

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.core.view.setPadding
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

    val REQUEST_CODE_BITMAP = 101
    val REQUEST_CODE_CAMERA_PERMISSIONS = 102

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPhotosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        photosViewModel.isLoading.observe(this, Observer {
            binding.isLoading.isVisible = it
            binding.ivIcon.isVisible = it
        })

        setUp()

    }

    private fun setUp() {
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
        //check permissions
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
                REQUEST_CODE_CAMERA_PERMISSIONS
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
            REQUEST_CODE_CAMERA_PERMISSIONS -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                dispatchPictureIntent()
            }
        }
    }

    private fun dispatchPictureIntent() {
        //Searching an camera app and take the photo

        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePicture ->
            takePicture.resolveActivity(packageManager)?.also {
                startActivityForResult(takePicture, REQUEST_CODE_BITMAP)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_BITMAP && resultCode == RESULT_OK) {
            //getting the photo and saving in the device
            data?.extras?.let {
                val imgBitmap = it.get("data") as Bitmap

                binding.ivIcon.setImageBitmap(imgBitmap)
                binding.ivIcon.setPadding(20)
                CoroutineScope(Dispatchers.IO).launch {
                    photosViewModel.insertImg(imageDomain = ImageDomain(imgBitmap))
                }
            }
        }
    }
}