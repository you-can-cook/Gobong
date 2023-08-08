package com.youcancook.gobong.ui.addRecipe

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.youcancook.gobong.adapter.RecipeListAdapter
import com.youcancook.gobong.adapter.bindingAdapter.addIngredient
import com.youcancook.gobong.databinding.ActivityAddRecipeBinding
import com.youcancook.gobong.model.RecipeAdd
import com.youcancook.gobong.ui.ImageActivity
import com.youcancook.gobong.util.ImageLoader

class AddRecipeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddRecipeBinding
    private val addRecipeViewModel: AddRecipeViewModel by viewModels()

    private var imagePickActivityLauncher: ActivityResultLauncher<Intent>? = null
    private val addStepBottomSheet = RecipeStepBottomFragment()
    private val recipeAdapter = RecipeListAdapter(onItemClick = {

    }, onAddItemClick = {
        addStepBottomSheet.show(supportFragmentManager, RecipeStepBottomFragment.TAG)
    })

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.run {
            vm = addRecipeViewModel
            lifecycleOwner = this@AddRecipeActivity
        }
        imagePickActivityLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == AppCompatActivity.RESULT_OK) {
                    println("restul!!")
                    val imageData = result.data?.getByteArrayExtra(ImageActivity.IMAGE_DATA_TAG)
                        ?: return@registerForActivityResult
                    addRecipeViewModel.setThumbnailByteArray(imageData)
                }
            }

        binding.run {
            rootLayout.setOnClickListener {
                addIngredientEditText.clearFocus()
            }

            closeButton.setOnClickListener {
                closeAlertDialog.show()
            }

            thumbnailImageView.setOnClickListener {
                getImageFromGallery()
            }

            addIngredientButton.setOnClickListener {
                addIngredientEditText.clearFocus()
                addIngredientEditText.isVisible = true
            }

            addIngredientEditText.setOnFocusChangeListener { v, hasFocus ->
                if (hasFocus.not()) {
                    val oldText = addRecipeViewModel.getIngredientInputText()
                    if (oldText.isNotEmpty()) {
                        ingredientGroup.addIngredient(oldText)
                        addIngredientEditText.text.clear()
                        addIngredientEditText.isVisible = false
                    }
                }
            }

            recyclerView.adapter = recipeAdapter

            recipeAdapter.submitList(
                listOf(
                    RecipeAdd()
                )
            )
        }
    }

    private fun getImageFromGallery() {
        Intent(this, ImageActivity::class.java).run {
            imagePickActivityLauncher?.launch(this)
        }
    }
}