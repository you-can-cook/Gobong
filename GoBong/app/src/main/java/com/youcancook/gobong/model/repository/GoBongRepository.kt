package com.youcancook.gobong.model.repository

import com.youcancook.gobong.model.Card
import com.youcancook.gobong.model.Filter
import com.youcancook.gobong.model.RecipePost
import com.youcancook.gobong.model.UploadRecipe
import com.youcancook.gobong.model.User

interface GoBongRepository {

    suspend fun getCurrentRecipe(recipePostId: Int): RecipePost

    suspend fun getFollowingRecipes(): List<Card>

    suspend fun getAllRecipes(): List<Card>

    suspend fun getFilteredRecipes(filter: Filter): List<Card>

    suspend fun getBookmarkedRecipes(): List<Card>
    suspend fun bookmarkRecipe(marked: Boolean)

    suspend fun deleteRecipe(recipeId: Int)
    suspend fun reviewRecipe(recipeId: Int, star: Int)

    suspend fun uploadRecipe(uploadRecipe: UploadRecipe)

    suspend fun getMyRecipes(): User
    suspend fun getUserRecipes(userId: String): User
}