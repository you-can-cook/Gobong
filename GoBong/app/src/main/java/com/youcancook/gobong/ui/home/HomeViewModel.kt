package com.youcancook.gobong.ui.home

import com.youcancook.gobong.model.Card
import com.youcancook.gobong.ui.base.NetworkViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HomeViewModel : NetworkViewModel() {

    private val _recipes = MutableStateFlow<List<Card>>(
        listOf(
            Card.createEmpty(),
            Card.createEmpty(),
            Card.createEmpty(),
            Card.createEmpty(),
            Card.createEmpty(),
            Card.createEmpty(),
            Card.createEmpty()
        )
    )

    //    private val _recipes = MutableStateFlow<List<Card>>(emptyList())
    val recipes: StateFlow<List<Card>> = _recipes


    fun follow(userNickname: String) {
        _recipes.value = _recipes.value.map {
            if (it.user.nickname == userNickname) {
                it.copy(user = it.user.copy(followed = true))
            } else {
                it
            }
        }
        println("follow ${recipes.value.joinToString(" ")}")
    }

    fun unFollow(userNickname: String) {
        _recipes.value = _recipes.value.map {
            if (it.user.nickname == userNickname) {
                it.copy(user = it.user.copy(followed = false))
            } else {
                it
            }
        }
        println("unfollow ${recipes.value.joinToString(" ")}")
    }
}