package com.youcancook.gobong.model.datasource

import com.youcancook.gobong.model.Card
import com.youcancook.gobong.model.network.GoBongService
import java.lang.Exception

class GoBongRemoteDataSource(
    private val goBongService: GoBongService,
) {

    suspend fun getFollowingRecipes(): List<Card> {
        return emptyList()
    }

    suspend fun getAllRecipes(): List<Card> {
        return emptyList()
    }

    suspend fun getFilteredRecipes(): List<Card> {
        return emptyList()
    }

    suspend fun getBookmarkedRecipes(): List<Card> {
        return emptyList()
    }

    fun bookmarkRecipe(marked: Boolean) {
        goBongService
    }

    fun reviewRecipe(star: Int) {
        goBongService
    }
}