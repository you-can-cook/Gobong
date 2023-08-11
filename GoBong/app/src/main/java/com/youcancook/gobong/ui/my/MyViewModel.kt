package com.youcancook.gobong.ui.my

import androidx.lifecycle.ViewModel
import com.youcancook.gobong.model.Card
import com.youcancook.gobong.model.User
import com.youcancook.gobong.ui.base.NetworkViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MyViewModel : NetworkViewModel() {

    private val _user = MutableStateFlow<User>(User())
    val user: StateFlow<User> = _user

    private val _recipes = MutableStateFlow<List<Card>>(emptyList())
    val recipes: StateFlow<List<Card>> get() = _recipes

}