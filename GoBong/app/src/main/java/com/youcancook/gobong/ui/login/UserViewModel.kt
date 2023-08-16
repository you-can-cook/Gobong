package com.youcancook.gobong.ui.login

import com.youcancook.gobong.model.User
import com.youcancook.gobong.model.UserToken
import com.youcancook.gobong.model.repository.UserRepositoryImpl
import com.youcancook.gobong.ui.base.NetworkViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

open class UserViewModel(
    private val userRepository: UserRepositoryImpl,
) : NetworkViewModel() {

    val nicknameInput = MutableStateFlow("")

    private val _profileImage = MutableStateFlow("")
    val profileImage: StateFlow<String> get() = _profileImage

    private val _profileImageByteArray = MutableStateFlow(byteArrayOf())
    val profileImageByteArray: StateFlow<ByteArray> get() = _profileImageByteArray

    private val _errorMessage = MutableStateFlow("")
    val errorMessage: StateFlow<String> get() = _errorMessage

    private val _token = MutableStateFlow(UserToken("", ""))

    fun setProfileImage(imageUrl: String) {
        _profileImage.value = imageUrl
    }

    fun setProfileImageByteArray(image: ByteArray) {
        _profileImageByteArray.value = image
    }

    fun setErrorMessage(message: String) {
        _errorMessage.value = message
    }

    fun setToken(token: UserToken) {
        _token.value = token
    }

    fun getToken() = _token.value

    protected fun getUser(): User {
        return User(
            _profileImage.value,
            nicknameInput.value,
        )
    }

}