package com.youcancook.gobong.model

data class UploadRecipe(
    val thumbnailByteArray: ByteArray,
    val title: String,
    val description: String = "",
    val ingredients: List<String>,
    val level: String,
    val recipes: List<RecipeStepAdded>,
)

data class RecipePost(
    val cardInfo: Card,
    val title: String,
    val description: String = "",
    val ingredients: List<String>,
    val level: String,
    val recipes: List<RecipeStepAdded>,
)