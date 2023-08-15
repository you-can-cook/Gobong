package com.youcancook.gobong.ui.my.setting

import com.youcancook.gobong.model.repository.UserRepositoryImpl
import com.youcancook.gobong.ui.login.UserViewModel

class SettingUserViewModel(
    private val userRepository: UserRepositoryImpl,
) : UserViewModel(userRepository) {

    fun updateUserProfile() {

    }
}