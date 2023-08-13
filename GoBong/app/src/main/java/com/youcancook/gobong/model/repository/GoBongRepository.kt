package com.youcancook.gobong.model.repository

import com.youcancook.gobong.model.datasource.GoBongRemoteDataSource

class GoBongRepository(
    private val goBongDataSource: GoBongRemoteDataSource,
) {

    fun bookmarkRecipe(marked: Boolean) {
        goBongDataSource.bookmarkRecipe(marked)
    }

    fun reviewRecipe(star: Int) {
        goBongDataSource.reviewRecipe(star)
    }
}