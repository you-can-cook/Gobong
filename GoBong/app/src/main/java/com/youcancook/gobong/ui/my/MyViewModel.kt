package com.youcancook.gobong.ui.my

import androidx.lifecycle.ViewModel
import com.youcancook.gobong.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MyViewModel : ViewModel() {

    private val _user = MutableStateFlow<User>(User())
    val user: StateFlow<User> = _user
}