package com.youcancook.gobong.model.network.dto

import com.google.gson.annotations.SerializedName

sealed interface RegisterDTO

data class RegisterWithProfileDTO(
    @SerializedName("nickname") val nickname: String,
    @SerializedName("provider") val provider: String,
    @SerializedName("oauthId") val oAuthId: String,
    @SerializedName("temporaryToken") val temporaryToken: String,
    @SerializedName("profileImageURL") val profileImageURL: String,
) : RegisterDTO

data class RegisterWithoutProfileImageDTO(
    @SerializedName("nickname") val nickname: String,
    @SerializedName("provider") val provider: String,
    @SerializedName("oauthId") val oAuthId: String,
    @SerializedName("temporaryToken") val temporaryToken: String,
) : RegisterDTO
