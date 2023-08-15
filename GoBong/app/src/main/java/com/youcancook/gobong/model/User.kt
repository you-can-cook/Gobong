package com.youcancook.gobong.model

import com.youcancook.gobong.model.network.dto.RegisterDTO

data class User(
    val profileUrl: String = "",
    val nickname: String = "쩝쩝박사",
    val userId: String = "",
    val recipe: String = "0",
    val follower: String = "0",
    val following: String = "0",
)

data class UserProfile(
    val profileUrl: String = "",
    val nickname: String = "쩝쩝박사",
    val userId: String = "",
    val notMine: Boolean = true,
    val followed: Boolean = false,
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

fun RegisterUser.toRegisterDTO(): RegisterDTO {
    return RegisterDTO(
        nickname,
        provider,
        oAuthId,
        temporaryToken,
        profileImageURL
    )
}
