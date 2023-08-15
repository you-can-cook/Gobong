package com.youcancook.gobong.model

data class RecipePost(
    val thumbnailByteArray: ByteArray,
    val title: String,
    val description: String = "",
    val ingredients: List<String>,
    val level: String,
    val recipes: List<RecipeStepAdded>,
)