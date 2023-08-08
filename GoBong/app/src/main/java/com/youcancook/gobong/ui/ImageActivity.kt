package com.youcancook.gobong.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.youcancook.gobong.R
import com.youcancook.gobong.databinding.ActivityImageBinding

class ImageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityImageBinding
    private var imagePickActivityLauncher: ActivityResultLauncher<Intent>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImageBinding.inflate(layoutInflater)

        setContentView(R.layout.activity_image)

        imagePickActivityLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    val imageUri = result.data?.data
                    if (imageUri == null) {
                        finish()
                        return@registerForActivityResult
                    }

                    val inputStream = contentResolver.openInputStream(imageUri)
                    val inputData = inputStream?.readBytes()

                    if (inputData == null) {
                        finish()
                        return@registerForActivityResult
                    }
                    inputStream.close()
                    sendImageByte(inputData)
                }
            }

        Intent().apply {
            type = "image/*"
            action = Intent.ACTION_PICK
        }.run {
            imagePickActivityLauncher?.launch(this)
        }
    }

    private fun sendImageByte(inputData: ByteArray) {
        setResult(RESULT_OK, Intent().putExtra(IMAGE_DATA_TAG, inputData))
        finish()
    }

    companion object {
        val IMAGE_DATA_TAG = "imageData"
    }
}