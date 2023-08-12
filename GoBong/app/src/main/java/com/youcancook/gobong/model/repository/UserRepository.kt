package com.youcancook.gobong.model.repository

import com.youcancook.gobong.model.RegisterUser
import com.youcancook.gobong.model.UserToken
import com.youcancook.gobong.model.datasource.UserDataSource

class UserRepository(
    private val userDataSource: UserDataSource,
) {

    suspend fun makeTemporaryToken(): String {
        return userDataSource.requestTemporaryToken()
    }

    suspend fun login() {

    }

    suspend fun register(registerUser: RegisterUser): UserToken {
        return userDataSource.requestRegister(registerUser)

    }
}