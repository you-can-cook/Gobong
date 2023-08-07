package com.youcancook.gobong.util

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class ImageLoader(private val activity: AppCompatActivity) {

    private var imagePickActivityLauncher: ActivityResultLauncher<Intent>? = null

    fun setLauncher(onImageLoaded: (ByteArray) -> Unit) {
        imagePickActivityLauncher =
            activity.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == AppCompatActivity.RESULT_OK) {
                    val imageUri = result.data?.data ?: return@registerForActivityResult
                    val inputStream = activity.contentResolver.openInputStream(imageUri)
                    val inputData = inputStream?.readBytes()
                        ?: return@registerForActivityResult
                    inputStream.close()
                    onImageLoaded(inputData)
                }
            }
    }

    fun getImageFromGallery() {
        Intent().apply {
            type = "image/*"
            action = Intent.ACTION_PICK
        }.run {
            imagePickActivityLauncher?.launch(this)
        }
    }
}