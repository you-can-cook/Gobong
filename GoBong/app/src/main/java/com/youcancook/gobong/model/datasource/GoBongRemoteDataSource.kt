package com.youcancook.gobong.model.datasource

import com.youcancook.gobong.model.Card
import com.youcancook.gobong.model.RecipePost
import com.youcancook.gobong.model.network.GoBongService
import com.youcancook.gobong.model.network.ImageService
import kotlinx.coroutines.flow.MutableStateFlow

class GoBongRemoteDataSource(
    private val goBongService: GoBongService,
    private val imageService: ImageService,
) {

    suspend fun getFollowingRecipes(): List<Card> {
        return emptyList()
    }

    suspend fun getAllRecipes(): List<Card> {
//        return emptyList()
        return listOf(
            Card.createEmpty(),
            Card.createEmpty(),
            Card.createEmpty(),
            Card.createEmpty(),
            Card.createEmpty()
        )
    }

    suspend fun getFilteredRecipes(): List<Card> {
//        return emptyList()
        return listOf(
            Card.createEmpty(),
            Card.createEmpty(),
            Card.createEmpty(),
            Card.createEmpty(),
            Card.createEmpty()
        )
    }

    suspend fun getBookmarkedRecipes(): List<Card> {
        return emptyList()
    }

    fun bookmarkRecipe(marked: Boolean) {
        goBongService
    }

    suspend fun deleteRecipe(postId: String) {

    }

    fun reviewRecipe(star: Int) {
        goBongService
    }

    suspend fun uploadRecipe(recipePost: RecipePost) {

    }
}