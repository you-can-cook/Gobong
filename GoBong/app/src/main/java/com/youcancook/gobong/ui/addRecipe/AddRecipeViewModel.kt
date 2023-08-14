package com.youcancook.gobong.ui.addRecipe

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.youcancook.gobong.model.Recipe
import com.youcancook.gobong.model.RecipeAdd
import com.youcancook.gobong.model.repository.GoBongRepository
import com.youcancook.gobong.ui.base.NetworkViewModel
import com.youcancook.gobong.util.NetworkState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AddRecipeViewModel(
    private val goBongRepository: GoBongRepository,
) : NetworkViewModel() {

    private val _thumbnailByteArray = MutableStateFlow(byteArrayOf(0))
    val thumbnailByteArray: StateFlow<ByteArray> get() = _thumbnailByteArray

    val titleInput = MutableStateFlow("")
    val descriptionInput = MutableStateFlow("")

    val ingredientInput = MutableStateFlow("")

    private val _level = MutableStateFlow("")
    val level: StateFlow<String> get() = _level

    private val recipeAdd = RecipeAdd()
    private val _recipes = MutableStateFlow<List<Recipe>>(
        mutableListOf(
            recipeAdd
        )
    )
    val recipes: StateFlow<List<Recipe>> get() = _recipes

    fun setThumbnailByteArray(byteArray: ByteArray) {
        _thumbnailByteArray.value = byteArray
    }

    fun getIngredientInputText() = ingredientInput.value

    fun setLevel(selectedLevel: String) {
        _level.value = selectedLevel
    }

    fun addNewRecipeStep(recipeStep: Recipe) {
        _recipes.value =
            _recipes.value.subList(0, _recipes.value.size - 1).plus(recipeStep).plus(recipeAdd)
    }

    fun replaceNewRecipeStep(recipeStep: Recipe) {
        _recipes.value = _recipes.value.map {
            if (it.id == recipeStep.id) recipeStep
            else it
        }
    }

    fun uploadNewRecipePost(ingredients: List<String>) {
        viewModelScope.launch {
            setNetworkState(NetworkState.LOADING)
            try {
                isValidateUpload(ingredients)
                upload()
                setNetworkState(NetworkState.SUCCESS)
            } catch (e: Exception) {
                setSnackBarMessage(e.message ?: "")
                setNetworkState(NetworkState.FAIL)
            }
            setNetworkState(NetworkState.DONE)
        }

    }

    private fun upload() {
        //TODO 네트워크 요청
    }

    private fun isValidateUpload(ingredients: List<String>) {
        if (_thumbnailByteArray.value.isEmpty()) throw Exception("대표사진을 올려주세요")
        if (titleInput.value.isEmpty()) throw Exception("제목을 입력해주세요")
        if (ingredients.isEmpty()) throw Exception("재료를 입력해주세요")
        if (level.value.isEmpty()) throw Exception("난이도를 선택해주세요")
        if (_recipes.value.size == 1) throw Exception("레시피를 추가해주세요")
    }
}