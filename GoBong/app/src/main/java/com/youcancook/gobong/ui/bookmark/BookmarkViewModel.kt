package com.youcancook.gobong.ui.bookmark

import androidx.lifecycle.viewModelScope
import com.youcancook.gobong.model.Card
import com.youcancook.gobong.model.repository.GoBongRepositoryImpl
import com.youcancook.gobong.ui.base.NetworkViewModel
import com.youcancook.gobong.util.NetworkState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BookmarkViewModel(
    private val goBongRepository: GoBongRepositoryImpl,
) : NetworkViewModel() {

    private val _recipes = MutableStateFlow<List<Card>>(emptyList())
    val recipes: StateFlow<List<Card>> = _recipes

    fun getBookmarkedRecipes() {
        viewModelScope.launch {
            setNetworkState(NetworkState.LOADING)
            try {
                requestBookmarkedRecipes()
                setNetworkState(NetworkState.SUCCESS)
            } catch (e: Exception) {
                setNetworkState(NetworkState.FAIL)
                setSnackBarMessage(e.message ?: "")
            }
            finishNetwork()
        }
    }

    private suspend fun requestBookmarkedRecipes() {
        _recipes.value = goBongRepository.getBookmarkedRecipes()
    }
}