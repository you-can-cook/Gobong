package com.youcancook.gobong.ui.search

import androidx.lifecycle.viewModelScope
import com.youcancook.gobong.model.Card
import com.youcancook.gobong.model.repository.GoBongRepository
import com.youcancook.gobong.ui.base.NetworkViewModel
import com.youcancook.gobong.util.NetworkState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.lang.Exception

class SearchViewModel(
    private val goBongRepository: GoBongRepository,
) : NetworkViewModel() {

    private val _recipes = MutableStateFlow<List<Card>>(emptyList())
    val recipes: StateFlow<List<Card>> get() = _recipes

    fun getAllRecipes() {
        viewModelScope.launch {
            setNetworkState(NetworkState.LOADING)
            try {
                requestAllRecipes()
                setNetworkState(NetworkState.SUCCESS)
            } catch (e: Exception) {
                setNetworkState(NetworkState.FAIL)
                setSnackBarMessage(e.message ?: "")
            }
            finishNetwork()
        }
    }

    private suspend fun requestAllRecipes() {
        _recipes.value = goBongRepository.getAllRecipes()
    }

}