package com.youcancook.gobong.model.network.dto

import com.google.gson.annotations.SerializedName
import com.youcancook.gobong.model.UserToken


data class TemporaryTokenDTO(
    @SerializedName("temporaryToken") val temporaryToken: String,
)

data class LoginDTO(
    @SerializedName("grantType") val grantType: String,
    @SerializedName("accessToken") val accessToken: String,
    @SerializedName("refreshToken") val refreshToken: String,
)


fun LoginDTO.toUserToken(): UserToken {
    return UserToken(
        accessToken,
        refreshToken
    )
}