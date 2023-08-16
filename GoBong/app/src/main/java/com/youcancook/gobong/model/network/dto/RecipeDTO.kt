package com.youcancook.gobong.model.network.dto

import com.google.gson.annotations.SerializedName

data class UploadRecipeDTO(
    @SerializedName("title") val title: String,
    @SerializedName("introduction") val introduction: String,
    @SerializedName("ingredients") val ingredients: List<String>,
    @SerializedName("difficulty") val difficulty: String,
    @SerializedName("thumbnailURL") val thumbnailURL: String,
    @SerializedName("recipeDetails") val recipeDetails: List<RecipeStepDTO>,
)

data class RecipeStepDTO(
    @SerializedName("content") val content: String,
    @SerializedName("imageURL") val imageURL: String,
    @SerializedName("cookTimeInSeconds") val cookTimeInSeconds: Int,
    @SerializedName("cookwares") val cookwares: List<String>,
)
