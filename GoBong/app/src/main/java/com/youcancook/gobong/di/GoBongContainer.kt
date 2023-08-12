package com.youcancook.gobong.di

import com.youcancook.gobong.model.datasource.GoBongRemoteDataSource
import com.youcancook.gobong.model.network.GoBongService
import com.youcancook.gobong.model.repository.GoBongRepository
import com.youcancook.gobong.util.BASE_URL
import retrofit2.Retrofit

class GoBongContainer {

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .build()
        .create(GoBongService::class.java)

    private val remoteDataSource = GoBongRemoteDataSource(retrofit)

    val goBongRepository = GoBongRepository(remoteDataSource)
}