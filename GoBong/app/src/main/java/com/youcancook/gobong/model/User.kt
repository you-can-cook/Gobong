package com.youcancook.gobong.model

import com.youcancook.gobong.model.network.dto.RegisterDTO
import com.youcancook.gobong.model.network.dto.RegisterWithProfileDTO
import com.youcancook.gobong.model.network.dto.RegisterWithoutProfileImageDTO

data class User(
    val profileUrl: String = "",
    val nickname: String = "쩝쩝박사",
    val recipe: String = "0",
    val follower: String = "0",
    val following: String = "0",
)

data class RegisterUser(
    val nickname: String,
    val provider: String,
    val oAuthId: String,
    val temporaryToken: String,
    val profileImageURL: String? = null,
)

data class UserToken(
    val accessToken: String,
    val refreshToken: String,
)

fun RegisterUser.toRegisterWithProfileDTO(): RegisterDTO {
    return RegisterWithProfileDTO(
        nickname,
        provider,
        oAuthId,
        temporaryToken,
        profileImageURL ?: ""
    )
}

fun RegisterUser.toRegisterWithoutProfileImageDTO(): RegisterWithoutProfileImageDTO {
    return RegisterWithoutProfileImageDTO(
        nickname,
        provider,
        oAuthId,
        temporaryToken
    )
}