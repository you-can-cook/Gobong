package com.youcancook.gobong.model.repository

import com.youcancook.gobong.model.LoginUser
import com.youcancook.gobong.model.RegisterUser
import com.youcancook.gobong.model.UserToken
import com.youcancook.gobong.model.datasource.UserDataSource

class UserRepositoryImpl(
    private val userDataSource: UserDataSource,
) : UserRepository {

    override suspend fun makeTemporaryToken(): String {
        return userDataSource.requestTemporaryToken()
    }

    override suspend fun makeAccessToken(refreshToken: String): String {
        return userDataSource.requestAccessToken(refreshToken)
    }

    override suspend fun login(user: LoginUser): UserToken {
        return userDataSource.requestLogin(user)
    }

    override suspend fun register(registerUser: RegisterUser): UserToken {
        return userDataSource.requestRegister(registerUser)

    }

    override suspend fun follow(userId: String) {
        userDataSource.requestFollow(userId)
    }

    override suspend fun unfollow(userId: String) {
        userDataSource.requestUnfollow(userId)
    }


}