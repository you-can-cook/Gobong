package com.youcancook.gobong.ui.search

import androidx.lifecycle.viewModelScope
import com.youcancook.gobong.model.Card
import com.youcancook.gobong.model.Filter
import com.youcancook.gobong.model.repository.GoBongRepository
import com.youcancook.gobong.model.repository.GoBongRepositoryImpl
import com.youcancook.gobong.model.repository.UserRepository
import com.youcancook.gobong.model.repository.UserRepositoryImpl
import com.youcancook.gobong.ui.base.NetworkViewModel
import com.youcancook.gobong.util.NetworkState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SearchViewModel(
    private val goBongRepository: GoBongRepository,
    private val userRepository: UserRepository,
) : NetworkViewModel() {

    private val _filter = MutableStateFlow(Filter.createEmpty())
    val filter = MutableStateFlow(Filter.createEmpty())

    private val _filtered = MutableStateFlow(false)
    val filtered: StateFlow<Boolean> get() = _filtered

    private val _recipes = MutableStateFlow<List<Card>>(emptyList())
    val recipes: StateFlow<List<Card>> get() = _recipes

    private fun setSearchWord(word: String) {
        setFilter(_filter.value.copy(searchWord = word))
    }

    fun getCurrentFilter() = _filter.value

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
        }
    }

    private suspend fun requestAllRecipes() {
        _recipes.value = goBongRepository.getAllRecipes()
    }

    fun setFilter(filter: Filter) {
        _filter.value = filter
        _filtered.value = _filter.value.isEmpty().not()
    }

    fun filter(newWord: String) {
        setSearchWord(newWord)

        if (_filter.value.isEmpty()) {
            getAllRecipes()
        } else {
            getFilteredData()
        }
    }

    private fun getFilteredData() {
        viewModelScope.launch {
            setNetworkState(NetworkState.LOADING)
            try {
                requestFilteredData()
                setNetworkState(NetworkState.SUCCESS)
            } catch (e: Exception) {
                setNetworkState(NetworkState.FAIL)
                setSnackBarMessage(e.message ?: "")
            }
        }
    }

    private suspend fun requestFilteredData() {
        _recipes.value = goBongRepository.getFilteredRecipes(filter.value)
    }

    fun follow(userId: String) {
        viewModelScope.launch {
            setNetworkState(NetworkState.LOADING)
            try {
                requestFollow(userId)
                setNetworkState(NetworkState.SUCCESS)
            } catch (e: Exception) {
                setNetworkState(NetworkState.FAIL)
                setSnackBarMessage(e.message ?: "")
            }
        }
    }

    fun unfollow(userId: String) {
        viewModelScope.launch {
            setNetworkState(NetworkState.LOADING)
            try {
                requestUnfollow(userId)
                setNetworkState(NetworkState.SUCCESS)
            } catch (e: Exception) {
                setNetworkState(NetworkState.FAIL)
                setSnackBarMessage(e.message ?: "")
            }
        }
    }

    private suspend fun requestFollow(userId: String) {
        userRepository.follow(userId)
        _recipes.value = _recipes.value.map {
            if (it.user.nickname == userId) {
                it.copy(user = it.user.copy(followed = true))
            } else {
                it
            }
        }
    }

    private suspend fun requestUnfollow(userId: String) {
        userRepository.unfollow(userId)
        _recipes.value = _recipes.value.map {
            if (it.user.nickname == userId) {
                it.copy(user = it.user.copy(followed = false))
            } else {
                it
            }
        }
    }

}