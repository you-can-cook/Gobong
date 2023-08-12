package com.youcancook.gobong.ui.addRecipe.bottom

import androidx.lifecycle.ViewModel
import com.youcancook.gobong.model.RecipeStepAdded
import com.youcancook.gobong.model.Tool
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class RecipeStepBottomViewModel : ViewModel() {
    private val _isSavedSuccess = MutableStateFlow(false)
    val isSavedSuccess: StateFlow<Boolean> get() = _isSavedSuccess

    private val _snackBarMessage = MutableStateFlow("")
    val snackBarMessage: StateFlow<String> get() = _snackBarMessage


    private val _thumbnailByteArray = MutableStateFlow(byteArrayOf(0))
    val thumbnailByteArray: StateFlow<ByteArray> get() = _thumbnailByteArray

    private val _tools = MutableStateFlow<List<Tool>>(
        listOf(
            Tool("전자레인지", false, true),
            Tool("에어프라이기", false, true),
            Tool("오븐", false, true),
            Tool("가스레인지", false, true),
            Tool("믹서", false, true),
            Tool("전기주전자", false, true),
            Tool("거품기", false, true)
        )
    )
    val tools: StateFlow<List<Tool>> get() = _tools

    private val _minute = MutableStateFlow("0")
    val minute: StateFlow<String> get() = _minute

    private val _second = MutableStateFlow("0")
    val second: StateFlow<String> get() = _second

    val descriptionInputText = MutableStateFlow("")

    fun setOldRecipe(recipe: RecipeStepAdded) {
        _thumbnailByteArray.value = recipe.photoUrl
        checkTools(recipe.tools)
        val times = recipe.time.split(" ")
        _minute.value = times[0].removeSuffix("분")
        _second.value = times[1].removeSuffix("초")
        descriptionInputText.value = recipe.description
    }

    fun setThumbnailByteArray(byteArray: ByteArray) {
        _thumbnailByteArray.value = byteArray
    }

    fun filterTools(searchInput: String) {
        _tools.value = if (searchInput.isEmpty()) {
            _tools.value.map {
                it.copy(isVisible = true)
            }
        } else {
            _tools.value.map {
                if (it.toolName.contains(searchInput)) it.copy(isVisible = true)
                else it.copy(isVisible = false)
            }

        }
    }

    fun checkTools(toolNames: List<String>) {
        _tools.value = _tools.value.map { tool ->
            if (tool.toolName in toolNames) tool.copy(isChecked = true)
            else if (tool.isVisible) {
                tool.copy(isChecked = false)
            } else {
                tool
            }
        }
    }

    fun clearTime() {
        _minute.value = "0"
        _second.value = "0"
    }

    fun addMinute(min: Int) {
        _minute.value = (_minute.value.toInt() + min).toString()
    }

    fun addSecond(sec: Int) {
        _second.value = (_second.value.toInt() + sec).toString()

        if (_second.value >= "60") {
            _second.value = (_second.value.toInt() - 60).toString()
            addMinute(1)
        }
    }

    fun saveRecipeStep() {
        try {
            isSaveValidate()
            _isSavedSuccess.value = true
        } catch (e: Exception) {
            _snackBarMessage.value = e.message ?: ""
        }
    }

    private fun isSaveValidate() {
        if (_thumbnailByteArray.value.isEmpty() && descriptionInputText.value.isEmpty())
            throw java.lang.Exception("사진이나 설명을 입력해주세요")
    }

    fun getNewRecipeStep(): RecipeStepAdded {
        return RecipeStepAdded(
            "${minute.value}분 ${second.value}초",
            tools.value.filter { it.isChecked }.map { it.toolName },
            thumbnailByteArray.value,
            descriptionInputText.value
        )
    }

    fun resetSave() {
        _thumbnailByteArray.value = ByteArray(0)
        _tools.value = _tools.value.map { it.copy(isChecked = false) }
        _minute.value = "0"
        _second.value = "0"
        descriptionInputText.value = ""

        _isSavedSuccess.value = false
    }
}