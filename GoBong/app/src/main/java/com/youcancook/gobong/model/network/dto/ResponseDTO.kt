package com.youcancook.gobong.model.network.dto

import com.google.gson.annotations.SerializedName
import com.youcancook.gobong.model.UserToken


data class TemporaryTokenResponseDTO(
    @SerializedName("temporaryToken") val temporaryToken: String,
)

data class LoginResponseDTO(
    @SerializedName("grantType") val grantType: String,
    @SerializedName("accessToken") val accessToken: String,
    @SerializedName("refreshToken") val refreshToken: String,
)

data class ImageUrlResponseDTO(
    @SerializedName("imageUrl") val imageUrl: String,
)


fun LoginResponseDTO.toUserToken(): UserToken {
    return UserToken(
        accessToken,
        refreshToken
    )
}