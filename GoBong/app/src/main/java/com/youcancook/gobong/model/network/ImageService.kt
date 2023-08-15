package com.youcancook.gobong.model.network

import com.youcancook.gobong.model.network.dto.ImageUrlResponseDTO
import retrofit2.Response
import retrofit2.http.POST

interface ImageService {

    @POST("/api/images/upload")
    fun postImage(

    ): Response<ImageUrlResponseDTO>

}