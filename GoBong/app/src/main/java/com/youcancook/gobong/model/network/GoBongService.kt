package com.youcancook.gobong.model.network

import com.youcancook.gobong.model.network.dto.RecipeFeedsResponseDTO
import com.youcancook.gobong.model.network.dto.UploadRecipeDTO
import com.youcancook.gobong.model.network.dto.UploadRecipeResponseDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface GoBongService {

    @GET("/api/feed/all")
    suspend fun getAllRecipes(
        @Header("Authorization") token: String,
        @Query("count") count: Int,
        @Query("last") last: Int? = null,
    ): Response<RecipeFeedsResponseDTO>

    @GET("/api/feed/following")
    suspend fun getFollowingRecipes(
        @Header("Authorization") token: String,
        @Query("count") count: Int,
        @Query("last") last: Int? = null,
    ): Response<RecipeFeedsResponseDTO>

    @GET("/api/feed/bookmarked")
    suspend fun getBookmarkedRecipes(
        @Header("Authorization") token: String,
        @Query("count") count: Int,
        @Query("last") last: Int? = null,
    ): Response<RecipeFeedsResponseDTO>

    @POST("/api/recipes")
    suspend fun uploadRecipe(
        @Header("Authorization") token: String,
        @Body data: UploadRecipeDTO,
    ): Response<UploadRecipeResponseDTO>

}