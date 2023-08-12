package com.youcancook.gobong.model.datasource

import com.youcancook.gobong.model.RegisterUser
import com.youcancook.gobong.model.UserToken
import com.youcancook.gobong.model.network.UserService
import com.youcancook.gobong.model.network.dto.LoginDTO
import com.youcancook.gobong.model.network.dto.toUserToken
import com.youcancook.gobong.model.toRegisterWithProfileDTO
import com.youcancook.gobong.model.toRegisterWithoutProfileImageDTO

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

    suspend fun requestLogin() {

    }

    suspend fun requestRegister(registerUser: RegisterUser): UserToken {
        val request = if (registerUser.profileImageURL == null) {
            registerUser.toRegisterWithoutProfileImageDTO()
        } else {
            registerUser.toRegisterWithProfileDTO()
        }
        println("register $request")
        val response = userService.postSignUp(request)
        println("register $response")
        if (response.isSuccessful) {
            return response.body()?.toUserToken() ?: throw Exception("네트워크 요청에 실패했습니다")
        } else {
            throw Exception("네트워크 요청에 실패했습니다")
        }
    }
}