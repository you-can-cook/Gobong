package com.youcancook.gobong.model.repository

import com.youcancook.gobong.model.Card
import com.youcancook.gobong.model.Filter
import com.youcancook.gobong.model.datasource.GoBongRemoteDataSource

class GoBongRepository(
    private val goBongDataSource: GoBongRemoteDataSource,
) {

    suspend fun getFollowingRecipes(): List<Card> {
        return goBongDataSource.getFollowingRecipes()
    }

    suspend fun getAllRecipes(): List<Card> {
        return goBongDataSource.getAllRecipes()
    }

    suspend fun getFilteredRecipes(filter: Filter): List<Card> {
        return goBongDataSource.getFilteredRecipes()
    }

    suspend fun getBookmarkedRecipes(): List<Card> {
        return goBongDataSource.getBookmarkedRecipes()
    }

    fun bookmarkRecipe(marked: Boolean) {
        goBongDataSource.bookmarkRecipe(marked)
    }

    fun reviewRecipe(star: Int) {
        goBongDataSource.reviewRecipe(star)
    }
}