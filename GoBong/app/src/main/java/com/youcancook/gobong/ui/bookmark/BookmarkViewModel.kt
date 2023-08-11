package com.youcancook.gobong.ui.bookmark

import androidx.lifecycle.ViewModel
import com.youcancook.gobong.model.Card
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class BookmarkViewModel : ViewModel() {

    private val _recipes = MutableStateFlow<List<Card>>(emptyList())
    val recipes: StateFlow<List<Card>> = _recipes
}