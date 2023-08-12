package com.youcancook.gobong.ui.search

import com.youcancook.gobong.model.Card
import com.youcancook.gobong.ui.base.NetworkViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SearchViewModel : NetworkViewModel() {

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