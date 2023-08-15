package com.youcancook.gobong.model.repository

import com.youcancook.gobong.model.Card
import com.youcancook.gobong.model.Filter
import com.youcancook.gobong.model.RecipePost

interface GoBongRepository {

    suspend fun getFollowingRecipes(): List<Card>

    suspend fun getAllRecipes(): List<Card>

    suspend fun getFilteredRecipes(filter: Filter): List<Card>

    suspend fun getBookmarkedRecipes(): List<Card>
    fun bookmarkRecipe(marked: Boolean)

    suspend fun deleteRecipe(postId: String)
    fun reviewRecipe(star: Int)

    suspend fun uploadRecipe(recipePost: RecipePost)
}