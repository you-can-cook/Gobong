package com.youcancook.gobong.model.network

import com.youcancook.gobong.model.network.dto.LoginDTO
import com.youcancook.gobong.model.network.dto.TemporaryTokenDTO
import retrofit2.Response
import retrofit2.http.POST

interface UserService {

    @POST("/api/users/temporary-token")
    suspend fun postTemporaryToken(

    ): Response<TemporaryTokenDTO>


    @POST("/api/users/signup")
    suspend fun postSignUp(

    ):Response<LoginDTO>
}