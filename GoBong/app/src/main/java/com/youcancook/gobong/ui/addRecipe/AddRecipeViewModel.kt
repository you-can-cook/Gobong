package com.youcancook.gobong.ui.addRecipe

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AddRecipeViewModel : ViewModel() {

    private val _thumbnailByteArray = MutableStateFlow(byteArrayOf(0))
    val thumbnailByteArray: StateFlow<ByteArray> get() = _thumbnailByteArray

    fun setThumbnailByteArray(byteArray: ByteArray) {
        _thumbnailByteArray.value = byteArray
    }
}