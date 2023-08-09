package com.youcancook.gobong.ui.addRecipe

import androidx.lifecycle.ViewModel
import com.youcancook.gobong.model.Tool
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filter

class RecipeStepBottomViewModel : ViewModel() {
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


}