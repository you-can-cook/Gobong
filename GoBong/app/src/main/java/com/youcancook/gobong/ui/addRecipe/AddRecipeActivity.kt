package com.youcancook.gobong.ui.addRecipe

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.view.children
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.chip.Chip
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.youcancook.gobong.R
import com.youcancook.gobong.adapter.RecipeListAdapter
import com.youcancook.gobong.adapter.bindingAdapter.addIngredient
import com.youcancook.gobong.databinding.ActivityAddRecipeBinding
import com.youcancook.gobong.model.RecipeStepAdded
import com.youcancook.gobong.ui.ImageActivity
import com.youcancook.gobong.ui.addRecipe.bottom.RecipeStepBottomFragment
import com.youcancook.gobong.ui.base.GoBongActivity
import com.youcancook.gobong.ui.base.NetworkActivity
import com.youcancook.gobong.ui.base.NetworkStateListener
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class AddRecipeActivity :
    NetworkActivity<ActivityAddRecipeBinding, AddRecipeViewModel>(R.layout.activity_add_recipe) {
    private var isEdit = false
    private var editId = 0L
    override val viewModel: AddRecipeViewModel by lazy {
        AddRecipeViewModel(appContainer.goBongRepository)
    }
    override val onNetworkStateChange: NetworkStateListener = object : NetworkStateListener {
        override fun onSuccess() {
            finish()
        }

        override fun onFail() {
        }

        override fun onDone() {
        }

    }

    private var imagePickActivityLauncher: ActivityResultLauncher<Intent>? = null
    private val addStepBottomSheet = RecipeStepBottomFragment()
        .apply {
            setOnDismissListener {
                //TODO 빈 값이면 리턴
                if (isEdit) {
                    val view = it as RecipeStepAdded
                    viewModel.replaceNewRecipeStep(view.copy(id = editId))
                } else {
                    viewModel.addNewRecipeStep(it)
                }
                isEdit = false
            }
        }
    private val recipeAdapter = RecipeListAdapter(onItemClick = {

    }, onEditItemClick = {
        addStepBottomSheet.show(supportFragmentManager, RecipeStepBottomFragment.TAG)
        addStepBottomSheet.apply {
            //TODO 레시피 수정
            isEdit = true
            editId = it.id
            setOldRecipe(it as RecipeStepAdded)
        }
    }, onAddItemClick = {
        isEdit = false
        addStepBottomSheet.show(supportFragmentManager, RecipeStepBottomFragment.TAG)
    })

    private val closeAlertDialog: AlertDialog by lazy {
        MaterialAlertDialogBuilder(this)
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

        binding.run {
            vm = viewModel
        }

        imagePickActivityLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    val imageData = result.data?.getByteArrayExtra(ImageActivity.IMAGE_DATA_TAG)
                        ?: return@registerForActivityResult
                    viewModel.setThumbnailByteArray(imageData)
                }
            }

        initListeners()

    }

    private fun initListeners() {
        binding.run {
            rootLayout.setOnClickListener {
                addIngredientEditText.clearFocus()
            }

            closeButton.setOnClickListener {
                closeAlertDialog.show()
            }

            uploadTextView.setOnClickListener {
                val ingredients = ingredientGroup.children.filter {
                    it.id != R.id.addIngredientEditText
                            && it.id != R.id.addIngredientButton
                }.map {
                    val view = it as Chip
                    view.text.toString()
                }.toList()
                viewModel.uploadNewRecipePost(ingredients)
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
                    val oldText = viewModel.getIngredientInputText()
                    if (oldText.isNotEmpty()) {
                        ingredientGroup.addIngredient(oldText)
                        addIngredientEditText.text.clear()
                    }
                    addIngredientEditText.isVisible = false
                }
            }

            levelGroup.addOnButtonCheckedListener { group, checkedId, isChecked ->
                val view = findViewById<Button>(checkedId)
                viewModel.setLevel(view.text.toString())
            }

            recyclerView.adapter = recipeAdapter

        }
    }

    private fun getImageFromGallery() {
        Intent(this, ImageActivity::class.java).run {
            imagePickActivityLauncher?.launch(this)
        }
    }
}