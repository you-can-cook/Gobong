package com.youcancook.gobong.ui.login

import androidx.lifecycle.viewModelScope
import com.youcancook.gobong.model.UserToken
import com.youcancook.gobong.model.repository.UserRepository
import com.youcancook.gobong.ui.base.NetworkViewModel
import com.youcancook.gobong.util.NetworkState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val repository: UserRepository,
) : NetworkViewModel() {

    private val _temporaryToken = MutableStateFlow("")
    val temporaryToken: StateFlow<String> get() = _temporaryToken

    private val _provider = MutableStateFlow("")
    val provider: StateFlow<String> get() = _provider

    private val _token = MutableStateFlow(UserToken("", ""))

    fun getTemporaryToken() = _temporaryToken.value

    fun getToken() = _token.value

    fun makeTemporaryToken() {
        viewModelScope.launch {
            setNetworkState(NetworkState.LOADING)
            try {
                requestTemporaryToken()
                setNetworkState(NetworkState.SUCCESS)
            } catch (e: Exception) {
                setNetworkState(NetworkState.FAIL)
                setSnackBarMessage(e.message ?: "")
            }
            setNetworkState(NetworkState.DONE)
        }
    }

    private suspend fun requestTemporaryToken() {
        _temporaryToken.value = repository.makeTemporaryToken()
    }

    fun login() {
        viewModelScope.launch {
            setNetworkState(NetworkState.LOADING)
            try {
                requestLogin()
                setNetworkState(NetworkState.SUCCESS)
            } catch (e: Exception) {
                setNetworkState(NetworkState.FAIL)
                setSnackBarMessage(e.message ?: "")
            }
            setNetworkState(NetworkState.DONE)
        }
    }

    private suspend fun requestLogin() {

    }

    fun loading() = setNetworkState(NetworkState.LOADING)
    fun success() = setNetworkState(NetworkState.SUCCESS)
    fun fail() = setNetworkState(NetworkState.FAIL)
    fun done() = finishNetwork()

}