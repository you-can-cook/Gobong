package com.youcancook.gobong.ui.my

import androidx.lifecycle.viewModelScope
import com.youcancook.gobong.model.Card
import com.youcancook.gobong.model.User
import com.youcancook.gobong.model.repository.GoBongRepositoryImpl
import com.youcancook.gobong.ui.base.NetworkViewModel
import com.youcancook.gobong.util.NetworkState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.lang.Exception

class MyViewModel(
    private val goBongRepository: GoBongRepositoryImpl,
) : NetworkViewModel() {

    private val _user = MutableStateFlow(User())
    val user: StateFlow<User> = _user

    private val _recipes = MutableStateFlow<List<Card>>(emptyList())
    val recipes: StateFlow<List<Card>> get() = _recipes

    fun getMyInfo() {
        viewModelScope.launch {
            setNetworkState(NetworkState.LOADING)
            try {
                requestMyInfo()
                setNetworkState(NetworkState.SUCCESS)
            } catch (e: Exception) {
                setNetworkState(NetworkState.FAIL)
                setSnackBarMessage(e.message ?: "")
            }
            finishNetwork()
        }
    }

    private fun requestMyInfo() {

    }
}