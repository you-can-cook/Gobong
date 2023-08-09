package com.youcancook.gobong.ui.addRecipe

import androidx.lifecycle.ViewModel
import com.youcancook.gobong.model.Recipe
import com.youcancook.gobong.model.RecipeAdd
import com.youcancook.gobong.model.RecipeStep
import com.youcancook.gobong.model.RecipeStepAdded
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AddRecipeViewModel : ViewModel() {

    private val _thumbnailByteArray = MutableStateFlow(byteArrayOf(0))
    val thumbnailByteArray: StateFlow<ByteArray> get() = _thumbnailByteArray

    val ingredientInput = MutableStateFlow("")

    private val _recipes = MutableStateFlow<List<Recipe>>(
        mutableListOf(
            RecipeAdd()
        )
    )
    val recipes: StateFlow<List<Recipe>> get() = _recipes

    fun setThumbnailByteArray(byteArray: ByteArray) {
        _thumbnailByteArray.value = byteArray
    }

    fun getIngredientInputText() = ingredientInput.value

    fun addNewRecipeStep(recipeStep: Recipe) {
        _recipes.value =
            _recipes.value.subList(0, _recipes.value.size - 1).plus(recipeStep).plus(RecipeAdd())
    }


}