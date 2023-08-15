package com.youcancook.gobong.ui.login

import androidx.lifecycle.viewModelScope
import com.youcancook.gobong.model.RegisterUser
import com.youcancook.gobong.model.User
import com.youcancook.gobong.model.UserToken
import com.youcancook.gobong.model.repository.UserRepositoryImpl
import com.youcancook.gobong.ui.base.NetworkViewModel
import com.youcancook.gobong.util.NetworkState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

open class UserViewModel(
    private val userRepository: UserRepositoryImpl,
) : NetworkViewModel() {


    val nicknameInput = MutableStateFlow("")

    private val _profileImage = MutableStateFlow("")
    val profileImage: StateFlow<String> get() = _profileImage

    private val _errorMessage = MutableStateFlow("")
    val errorMessage: StateFlow<String> get() = _errorMessage

    private val _token = MutableStateFlow(UserToken("", ""))

    fun setProfileImage(image: ByteArray) {

    }

    fun getToken() = _token.value


    protected fun getUser(): User {
        return User(
            _profileImage.value,
            nicknameInput.value,
        )
    }

}