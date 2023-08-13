package com.youcancook.gobong.ui.home

import androidx.lifecycle.viewModelScope
import com.youcancook.gobong.model.Card
import com.youcancook.gobong.model.repository.GoBongRepository
import com.youcancook.gobong.ui.base.NetworkViewModel
import com.youcancook.gobong.util.NetworkState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.lang.Exception

class HomeViewModel(
    private val goBongRepository: GoBongRepository,
) : NetworkViewModel() {

//    private val _recipes = MutableStateFlow<List<Card>>(
//        listOf(
//            Card.createEmpty(),
//            Card.createEmpty(),
//            Card.createEmpty(),
//            Card.createEmpty(),
//            Card.createEmpty(),
//            Card.createEmpty(),
//            Card.createEmpty()
//        )
//    )

    private val _recipes = MutableStateFlow<List<Card>>(emptyList())
    val recipes: StateFlow<List<Card>> = _recipes

    fun getFollowingRecipes() {
        viewModelScope.launch {
            setNetworkState(NetworkState.LOADING)
            try {
                requestFollowingRecipes()
                setNetworkState(NetworkState.SUCCESS)
                println("!!")
            } catch (e: Exception) {
                setNetworkState(NetworkState.FAIL)
                setSnackBarMessage(e.message ?: "")
            }
            finishNetwork()
        }
    }

    private suspend fun requestFollowingRecipes() {
        _recipes.value = goBongRepository.getFollowingRecipes()
    }

    fun follow(userNickname: String) {
        _recipes.value = _recipes.value.map {
            if (it.user.nickname == userNickname) {
                it.copy(user = it.user.copy(followed = true))
            } else {
                it
            }
        }
    }

    fun unFollow(userNickname: String) {
        _recipes.value = _recipes.value.map {
            if (it.user.nickname == userNickname) {
                it.copy(user = it.user.copy(followed = false))
            } else {
                it
            }
        }
    }
}