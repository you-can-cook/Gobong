package com.youcancook.gobong.ui.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

abstract class GoBongViewModel : ViewModel() {

    private val _snackBarMessage = MutableStateFlow("")
    val snackBarMessage: StateFlow<String> get() = _snackBarMessage

    fun setSnackBarMessage(message: String) {
        _snackBarMessage.value = message
    }
}