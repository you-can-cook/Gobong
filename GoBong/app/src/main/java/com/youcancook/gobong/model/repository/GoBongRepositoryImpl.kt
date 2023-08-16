package com.youcancook.gobong.model.repository

import com.youcancook.gobong.model.Card
import com.youcancook.gobong.model.Filter
import com.youcancook.gobong.model.UploadRecipe
import com.youcancook.gobong.model.User
import com.youcancook.gobong.model.datasource.GoBongRemoteDataSource

class GoBongRepositoryImpl(
    private val goBongDataSource: GoBongRemoteDataSource,
) : GoBongRepository {
    override suspend fun getCurrentRecipe(recipePostId: String): Card {
        return goBongDataSource.getCurrentRecipe(recipePostId)
    }

    override suspend fun getFollowingRecipes(): List<Card> {
        return goBongDataSource.getFollowingRecipes()
    }

    override suspend fun getAllRecipes(): List<Card> {
        return goBongDataSource.getAllRecipes()
    }

    override suspend fun getFilteredRecipes(filter: Filter): List<Card> {
        return goBongDataSource.getFilteredRecipes()
    }

    override suspend fun getBookmarkedRecipes(): List<Card> {
        return goBongDataSource.getBookmarkedRecipes()
    }

    override suspend fun bookmarkRecipe(marked: Boolean) {
        goBongDataSource.bookmarkRecipe(marked)
    }

    override suspend fun deleteRecipe(postId: String) {
        goBongDataSource.deleteRecipe(postId)
    }

    override suspend fun reviewRecipe(star: Int) {
        goBongDataSource.reviewRecipe(star)
    }

    override suspend fun uploadRecipe(uploadRecipe: UploadRecipe) {
        goBongDataSource.uploadRecipe(uploadRecipe)
    }

    override suspend fun getMyRecipes(): User {
        return goBongDataSource.getMyRecipes()
    }

    override suspend fun getUserRecipes(userId: String): User {
        return goBongDataSource.getUserRecipes(userId)
    }
}