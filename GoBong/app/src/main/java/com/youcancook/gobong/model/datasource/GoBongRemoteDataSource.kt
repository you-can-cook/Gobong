package com.youcancook.gobong.model.datasource

import com.youcancook.gobong.model.network.GoBongService

class GoBongRemoteDataSource(
    private val goBongService: GoBongService,
) {

    fun bookmarkRecipe(marked: Boolean) {
        goBongService
    }

    fun reviewRecipe(star: Int) {
        goBongService
    }
}