package com.youcancook.gobong.model

sealed interface Recipe

data class RecipeStep(
    val user: User = User(),
    val thumbnailUrl: String = "",
    val title: String = "내 레시피 대박임",
    val bookmark: String = "0",
    val time: String = "5분",
    val tools: List<String> = listOf("전자레인지"),
    val level: String = "쉬워요",
    val star: String = "3.2공기",
) : Recipe

data class RecipeAdd(
    val user: User = User(),
    val thumbnailUrl: String = "",
    val title: String = "내 레시피 대박임",
    val bookmark: String = "0",
    val time: String = "5분",
    val tools: List<String> = listOf("전자레인지"),
    val level: String = "쉬워요",
    val star: String = "3.2공기",
) : Recipe
