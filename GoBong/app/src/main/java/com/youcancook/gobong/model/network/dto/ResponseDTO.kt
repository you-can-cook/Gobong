package com.youcancook.gobong.model.network.dto

import com.google.gson.annotations.SerializedName
import com.youcancook.gobong.model.Card
import com.youcancook.gobong.model.UserProfile
import com.youcancook.gobong.model.UserToken
import com.youcancook.gobong.util.toStarRating
import com.youcancook.gobong.util.toTime

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

data class AuthorDTO(
    @SerializedName("id") val id: Int,
    @SerializedName("nickname") val nickname: String,
    @SerializedName("myself") val myself: Boolean,
    @SerializedName("following") val following: Boolean,
)

data class FeedDTO(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("thumbnailURL") val thumbnailURL: String? = null,
    @SerializedName("author") val author: AuthorDTO,
    @SerializedName("totalBookmarkCount") val totalBookmarkCount: Int,
    @SerializedName("totalCookTimeInSeconds") val totalCookTimeInSeconds: Int,
    @SerializedName("cookwares") val cookwares: List<String>,
    @SerializedName("difficulty") val difficulty: String,
    @SerializedName("averageRating") val averageRating: Double,
)

data class RecipeFeedsResponseDTO(
    @SerializedName("feed") val feeds: List<FeedDTO>,
    @SerializedName("hasNext") val hasNext: Boolean,
)

data class UploadRecipeResponseDTO(
    @SerializedName("id") val id: Int,
)

fun LoginResponseDTO.toUserToken(): UserToken {
    return UserToken(
        accessToken,
        refreshToken
    )
}


fun FeedDTO.toCard(): Card {
    return Card(
        id = id.toString(),
        user = UserProfile(
            profileUrl = "",
            userId = author.id.toString(),
            notMine = author.myself.not(),
            followed = author.following
        ),
        thumbnailUrl = thumbnailURL ?: "",
        title = title,
        bookmark = totalBookmarkCount.toString(),
        bookmarked = false,
        time = totalCookTimeInSeconds.toTime(),
        tools = cookwares,
        level = difficulty,
        star = averageRating.toStarRating(),
        description = "",
        ingredients = emptyList(),
        emptyList()
    )
}