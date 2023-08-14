package com.youcancook.gobong.ui.login

import androidx.lifecycle.viewModelScope
import com.youcancook.gobong.model.RegisterUser
import com.youcancook.gobong.model.UserToken
import com.youcancook.gobong.model.repository.UserRepositoryImpl
import com.youcancook.gobong.ui.base.NetworkViewModel
import com.youcancook.gobong.util.NetworkState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserViewModel(
    private val repository: UserRepositoryImpl,
) : NetworkViewModel() {

    private val _loginAuth = MutableStateFlow(LoginAuth("", "", ""))

    val nicknameInput = MutableStateFlow("")

    private val _profileImage = MutableStateFlow("")
    val profileImage: StateFlow<String> get() = _profileImage

    private val _errorMessage = MutableStateFlow("")
    val errorMessage: StateFlow<String> get() = _errorMessage

    private val _token = MutableStateFlow(UserToken("", ""))

    private val _isTokenExpired = MutableStateFlow(false)
    val isTokenExpired: StateFlow<Boolean> get() = _isTokenExpired

    fun setLoginAuth(auth: LoginAuth) {
        _loginAuth.value = auth
    }

    fun setProfileImage(image: ByteArray) {

    }

    fun getToken() = _token.value

    fun registerNickname() {
        if (nicknameInput.value.isEmpty()) {
            _errorMessage.value = "닉네임을 입력하세요"
            return
        }

        viewModelScope.launch {
            setNetworkState(NetworkState.LOADING)
            try {
                registerUser()
                setNetworkState(NetworkState.SUCCESS)
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: ""
                setNetworkState(NetworkState.FAIL)
            }
            setNetworkState(NetworkState.DONE)
        }
    }

    private suspend fun registerUser() {
        val registerUser = RegisterUser(
            nicknameInput.value,
            _loginAuth.value.provider,
            _loginAuth.value.oauthId,
            _loginAuth.value.temporaryToken,
            _profileImage.value
        )
        val response = repository.register(registerUser)
        println("response $response")
    }

}