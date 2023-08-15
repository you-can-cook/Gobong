package com.youcancook.gobong.model.network

import com.youcancook.gobong.model.network.dto.LoginDTO
import com.youcancook.gobong.model.network.dto.LoginResponseDTO
import com.youcancook.gobong.model.network.dto.RegisterDTO
import com.youcancook.gobong.model.network.dto.TemporaryTokenResponseDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface UserService {

    @POST("/api/users/temporary-token")
    suspend fun postTemporaryToken(

    ): Response<TemporaryTokenResponseDTO>

    @POST("/api/users/login")
    suspend fun postLogin(
        @Body data: LoginDTO,
    ): Response<LoginResponseDTO>

    @POST("/api/users/signup")
    suspend fun postSignUp(
        @Body data: RegisterDTO,
    ): Response<LoginResponseDTO>

    @POST("/api/follow/{folloeeId}")
    suspend fun follow(
        @Path("folloeeId") folloeeId: String,
    ): Response<Any>

    @POST("/api/unfollow/{folloeeId}")
    suspend fun unfollow(
        @Path("folloeeId") folloeeId: String,
    ): Response<Any>
}