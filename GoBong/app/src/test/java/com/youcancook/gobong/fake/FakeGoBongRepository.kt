package com.youcancook.gobong.fake

import com.youcancook.gobong.model.Card
import com.youcancook.gobong.model.Filter
import com.youcancook.gobong.model.RecipePost
import com.youcancook.gobong.model.repository.GoBongRepository

class FakeGoBongRepository : GoBongRepository {
    override suspend fun getFollowingRecipes(): List<Card> {
        throw Exception("네트워크 오류")
    }

    override suspend fun getAllRecipes(): List<Card> {
        throw Exception("네트워크 오류")
    }

    override suspend fun getFilteredRecipes(filter: Filter): List<Card> {
        throw Exception("네트워크 오류")
    }

    override suspend fun getBookmarkedRecipes(): List<Card> {
        throw Exception("네트워크 오류")
    }

    override fun bookmarkRecipe(marked: Boolean) {
        throw Exception("네트워크 오류")
    }

    override suspend fun deleteRecipe(postId: String) {
        throw Exception("네트워크 오류")
    }

    override fun reviewRecipe(star: Int) {
        throw Exception("네트워크 오류")
    }

    override suspend fun uploadRecipe(recipePost: RecipePost) {
        throw Exception("네트워크 오류")
    }
}