package com.youcancook.gobong.ui.search

import androidx.lifecycle.ViewModel
import com.youcancook.gobong.model.Card
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SearchViewModel : ViewModel() {

    //    private val _recipes = MutableStateFlow<List<Card>>(
//        listOf(
//            Card(),
//            Card(),
//            Card(),
//            Card(),
//            Card(),
//            Card(),
//            Card()
//        )
//    )
    private val _recipes = MutableStateFlow<List<Card>>(emptyList())
    val recipes: StateFlow<List<Card>> get() = _recipes


}