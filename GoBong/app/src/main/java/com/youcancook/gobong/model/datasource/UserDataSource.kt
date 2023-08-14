package com.youcancook.gobong.model.datasource

import com.youcancook.gobong.model.RegisterUser
import com.youcancook.gobong.model.UserToken
import com.youcancook.gobong.model.network.UserService
import com.youcancook.gobong.model.network.dto.LoginDTO
import com.youcancook.gobong.model.network.dto.toUserToken
import com.youcancook.gobong.model.toRegisterDTO

class UserDataSource(
    private val userService: UserService,
) {
    suspend fun requestTemporaryToken(): String {
        val response = userService.postTemporaryToken()
        println("Response ${response.body()}")
        if (response.isSuccessful) {
            return response.body()?.temporaryToken ?: throw Exception("네트워크 요청에 실패했습니다")
        } else {
            throw Exception("네트워크 요청에 실패했습니다")
        }
    }

    suspend fun requestAccessToken(refreshToken: String): String {
        return ""
    }

    suspend fun requestLogin() {

    }

    suspend fun requestRegister(registerUser: RegisterUser): UserToken {
        val response = userService.postSignUp(registerUser.toRegisterDTO())
        if (response.isSuccessful) {
            return response.body()?.toUserToken() ?: throw Exception("네트워크 요청에 실패했습니다")
        } else {
            throw Exception("네트워크 요청에 실패했습니다")
        }
    }


    suspend fun requestFollow(userId: String) {

    }

    suspend fun requestUnfollow(userId: String) {

    }
}