package com.youcancook.gobong.model

import com.youcancook.gobong.model.network.dto.RecipeStepAddedDTO
import com.youcancook.gobong.model.network.dto.RecipeStepDTO
import com.youcancook.gobong.model.network.dto.UploadRecipeDTO
import com.youcancook.gobong.util.toEnglishTool
import com.youcancook.gobong.util.toSeconds

data class UploadRecipe(
    val thumbnailByteArray: ByteArray,
    val title: String,
    val description: String = "",
    val ingredients: List<String>,
    val level: String,
    val recipes: List<RecipeStepAdded>,
)

data class RecipePost(
    val id: String,
    val cardInfo: Card,
    val recipes: List<RecipeStep>,
)

fun UploadRecipe.toUploadRecipeDTO(
    thumbnailUrl: String,
    recipeStepDTO: List<RecipeStepAddedDTO>,
): UploadRecipeDTO {
    println("uploadRecipe ${recipeStepDTO.joinToString("\n")}")
    return UploadRecipeDTO(
        title,
        description,
        ingredients,
        level,
        thumbnailUrl,
        recipeStepDTO
    )
}

fun RecipeStepAdded.toRecipeStepAddedDTO(imageUrl: String): RecipeStepAddedDTO {
    return RecipeStepAddedDTO(
        content = description,
        imageURL = imageUrl,
        cookTimeInSeconds = time.toSeconds(),
        cookwares = tools.map { it.toEnglishTool() }
    )
}
