package com.youcancook.gobong.model.network.dto

import com.google.gson.annotations.SerializedName
import com.youcancook.gobong.model.RecipePost
import com.youcancook.gobong.model.RecipeStep
import com.youcancook.gobong.util.toKoreanTool
import com.youcancook.gobong.util.toTime
import retrofit2.Response

data class UploadRecipeDTO(
    @SerializedName("title") val title: String,
    @SerializedName("introduction") val introduction: String,
    @SerializedName("ingredients") val ingredients: List<String>,
    @SerializedName("difficulty") val difficulty: String,
    @SerializedName("thumbnailURL") val thumbnailURL: String,
    @SerializedName("recipeDetails") val recipeDetails: List<RecipeStepAddedDTO>,
)

data class RecipeStepAddedDTO(
    @SerializedName("content") val content: String,
    @SerializedName("imageURL") val imageURL: String? = null,
    @SerializedName("cookTimeInSeconds") val cookTimeInSeconds: Int,
    @SerializedName("cookwares") val cookwares: List<String>,
)

data class RecipeStepDTO(
    @SerializedName("id") val id: String,
    @SerializedName("content") val content: String,
    @SerializedName("imageURL") val imageURL: String? = null,
    @SerializedName("cookTimeInSeconds") val cookTimeInSeconds: Int,
    @SerializedName("cookwares") val cookwares: List<String>,
    @SerializedName("step") val step: Int,
)

data class CurrentRecipeDTO(
    @SerializedName("id") val id: Int,
    @SerializedName("summary") val summary: FeedDTO,
    @SerializedName("introduction") val introduction: String,
    @SerializedName("ingredients") val ingredients: List<String>,
    @SerializedName("recipeDetails") val recipeDetails: List<RecipeStepDTO>,
)

data class ReviewDTO(
    @SerializedName("recipeId") val recipeId: Int,
    @SerializedName("score") val score: Int,
)

fun RecipeStepDTO.toRecipeStep(): RecipeStep {
    return RecipeStep(
        time = cookTimeInSeconds.toTime(),
        tools = cookwares.map { it.toKoreanTool() },
        photoUrl = imageURL ?: "",
        description = content,
        id = id.toLong()
    )
}

fun CurrentRecipeDTO.toRecipePost(): RecipePost {
    return RecipePost(
        id = id,
        cardInfo = summary.toCard(),
        recipes = recipeDetails.map { it.toRecipeStep() }
    )
}
