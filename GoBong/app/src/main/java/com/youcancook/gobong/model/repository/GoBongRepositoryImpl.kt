package com.youcancook.gobong.model.repository

import com.youcancook.gobong.model.Card
import com.youcancook.gobong.model.Filter
import com.youcancook.gobong.model.datasource.GoBongRemoteDataSource

class GoBongRepositoryImpl(
    private val goBongDataSource: GoBongRemoteDataSource,
) : GoBongRepository {

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

    override fun bookmarkRecipe(marked: Boolean) {
        goBongDataSource.bookmarkRecipe(marked)
    }

    override suspend fun deleteRecipe(postId: String) {
        goBongDataSource.deleteRecipe(postId)
    }

    override fun reviewRecipe(star: Int) {
        goBongDataSource.reviewRecipe(star)
    }
}