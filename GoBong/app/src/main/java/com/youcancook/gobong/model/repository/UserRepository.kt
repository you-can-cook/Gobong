package com.youcancook.gobong.model.repository

import com.youcancook.gobong.model.RegisterUser
import com.youcancook.gobong.model.UserToken
import com.youcancook.gobong.model.datasource.UserDataSource

interface UserRepository {

    suspend fun makeTemporaryToken(): String

    suspend fun makeAccessToken(refreshToken: String): String

    suspend fun login()

    suspend fun register(registerUser: RegisterUser): UserToken

    suspend fun follow(userId: String)

    suspend fun unfollow(userId: String)

}