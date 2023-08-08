package com.youcancook.gobong.ui.addRecipe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.view.children
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.youcancook.gobong.R
import com.youcancook.gobong.databinding.ActivityAddRecipeBinding
import com.youcancook.gobong.util.ImageLoader

class AddRecipeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddRecipeBinding
    private val addRecipeViewModel: AddRecipeViewModel by viewModels()

    private val closeAlertDialog: AlertDialog by lazy {
        AlertDialog.Builder(this)
            .setMessage("레시피 게시를 종료하시겠습니까?")
            .setPositiveButton("네") { _, _ ->
                finish()
            }
            .setNegativeButton("아니오") { _, _ ->

            }
            .setCancelable(true)
            .create()
    }

    private val imageLoader = ImageLoader(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.run {
            vm = addRecipeViewModel
            lifecycleOwner = this@AddRecipeActivity
        }

        imageLoader.setLauncher {
            addRecipeViewModel.setThumbnailByteArray(it)
        }

        binding.run {
            closeButton.setOnClickListener {
                closeAlertDialog.show()
            }

            thumbnailImageView.setOnClickListener {
                imageLoader.getImageFromGallery()
            }

            addIngredientButton.setOnClickListener {
//                ingredientGroup.addView(
//                    EditText(
//                        this@AddRecipeActivity,
//                        null,
//                        R.attr.IngredientEditChips
//                    ).apply { setText("11!!") }, 0
//                )
            }
        }
    }
}