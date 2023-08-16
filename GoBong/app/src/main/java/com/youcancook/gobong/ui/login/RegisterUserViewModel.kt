package com.youcancook.gobong.ui.login

import androidx.lifecycle.viewModelScope
import com.youcancook.gobong.model.LoginUser
import com.youcancook.gobong.model.RegisterUser
import com.youcancook.gobong.model.repository.UserRepositoryImpl
import com.youcancook.gobong.util.NetworkState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RegisterUserViewModel(
    private val userRepository: UserRepositoryImpl,
) : UserViewModel(userRepository) {

    private val _loginUser = MutableStateFlow(LoginUser("", "", ""))

    private val _temporaryToken = MutableStateFlow("")
    val temporaryToken: StateFlow<String> get() = _temporaryToken

    private val _isLoginFailed = MutableStateFlow(false)

    private val _provider = MutableStateFlow("")
    val provider: StateFlow<String> get() = _provider

    fun setLoginUser(user: LoginUser) {
        _loginUser.value = user
    }

    fun getLoginFailed() = _isLoginFailed.value

    fun getTemporaryToken() = _temporaryToken.value

    fun makeTemporaryToken() {
        viewModelScope.launch {
            setNetworkState(NetworkState.LOADING)
            try {
                requestTemporaryToken()
            } catch (e: Exception) {
                setNetworkState(NetworkState.FAIL)
                setSnackBarMessage(e.message ?: "")
            }
        }
    }

    private suspend fun requestTemporaryToken() {
        _temporaryToken.value = userRepository.makeTemporaryToken()
    }

    fun login() {
        viewModelScope.launch {
            setNetworkState(NetworkState.LOADING)
            try {
                requestLogin()
                setNetworkState(NetworkState.SUCCESS)
            } catch (e: Exception) {
                setSnackBarMessage(e.message ?: "")
                _isLoginFailed.value = true
                setNetworkState(NetworkState.FAIL)
            }
        }
    }

    private suspend fun requestLogin() {
        setToken(
            userRepository.login(
                _loginUser.value
            )
        )
    }

    fun registerNickname() {
        if (nicknameInput.value.isEmpty()) {
            setSnackBarMessage("닉네임을 입력하세요")
            return
        }

        viewModelScope.launch {
            setNetworkState(NetworkState.LOADING)
            try {
                registerUser()
                setNetworkState(NetworkState.SUCCESS)
            } catch (e: Exception) {
                setErrorMessage(e.message ?: "")
                setNetworkState(NetworkState.DONE)
            }
        }
    }

    private suspend fun registerUser() {
        val user = getUser()
        val registerUser = RegisterUser(
            user.nickname,
            _loginUser.value.provider,
            _loginUser.value.oAuthId,
            _loginUser.value.temporaryToken,
            user.profileUrl
        )
        setToken(userRepository.register(registerUser))
        println("response ${getToken()}")
    }

    fun loading() = setNetworkState(NetworkState.LOADING)

    fun fail() = setNetworkState(NetworkState.FAIL)
    fun done() = finishNetwork()

}