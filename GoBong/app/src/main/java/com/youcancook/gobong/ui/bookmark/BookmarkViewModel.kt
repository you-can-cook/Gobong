package com.youcancook.gobong.ui.bookmark

import androidx.lifecycle.ViewModel
import com.youcancook.gobong.model.Card
import com.youcancook.gobong.ui.base.NetworkViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class BookmarkViewModel : NetworkViewModel() {

    private val _recipes = MutableStateFlow<List<Card>>(emptyList())
    val recipes: StateFlow<List<Card>> = _recipes
}