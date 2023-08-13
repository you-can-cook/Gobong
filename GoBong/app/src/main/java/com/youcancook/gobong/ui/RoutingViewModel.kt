package com.youcancook.gobong.ui

import androidx.lifecycle.viewModelScope
import com.youcancook.gobong.model.repository.UserRepository
import com.youcancook.gobong.ui.base.NetworkViewModel
import com.youcancook.gobong.ui.login.UserViewModel
import com.youcancook.gobong.util.NetworkState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.lang.Exception

class RoutingViewModel(
    private val userRepository: UserRepository,
) : NetworkViewModel() {

    private val _accessToken = MutableStateFlow("")
    val accessToken: StateFlow<String> get() = _accessToken

    fun getAccessToken(refreshToken: String) {
        viewModelScope.launch {
            try {
                getNewAccessToken(refreshToken)
            } catch (e: Exception) {
                setNetworkState(NetworkState.FAIL)
            }
        }
    }

    private suspend fun getNewAccessToken(refreshToken: String) {
        _accessToken.value = userRepository.makeAccessToken(refreshToken)
    }
}