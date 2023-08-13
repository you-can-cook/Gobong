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

    suspend fun makeAccessToken(refreshToken: String): String {
        return userDataSource.requestAccessToken(refreshToken)
    }

    suspend fun login() {

    }

    suspend fun register(registerUser: RegisterUser): UserToken {
        return userDataSource.requestRegister(registerUser)

    }

    suspend fun follow(userId:String){
        userDataSource.requestFollow(userId)
    }

    suspend fun unfollow(userId:String){
        userDataSource.requestUnfollow(userId)
    }


}