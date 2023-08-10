package com.youcancook.gobong.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.youcancook.gobong.model.Card
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HomeViewModel : ViewModel() {

    //    private val _recipes = MutableStateFlow<List<Card>>(listOf(
//        Card(),
//        Card(),
//        Card(),
//        Card(),
//        Card(),
//        Card(),
//        Card()
//    ))
    private val _recipes = MutableStateFlow<List<Card>>(emptyList())
    val recipes: StateFlow<List<Card>> = _recipes
}