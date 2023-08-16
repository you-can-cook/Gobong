package com.youcancook.gobong.model.datasource

import com.youcancook.gobong.model.LoginUser
import com.youcancook.gobong.model.RegisterUser
import com.youcancook.gobong.model.UserToken
import com.youcancook.gobong.model.network.ImageService
import com.youcancook.gobong.model.network.UserService
import com.youcancook.gobong.model.network.dto.RefreshTokenDTO
import com.youcancook.gobong.model.network.dto.toUserToken
import com.youcancook.gobong.model.toLoginDTO
import com.youcancook.gobong.model.toRegisterDTO
import com.youcancook.gobong.util.REFRESH_TOKEN
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody


class UserDataSource(
    private val userService: UserService,
    private val imageService: ImageService,
) {
    suspend fun requestTemporaryToken(): String {
        val response = userService.postTemporaryToken()
        println("Response ${response.body()}")
        if (response.isSuccessful) {
            return response.body()?.temporaryToken ?: throw Exception("네트워크 요청에 실패했습니다")
        } else {
            throw Exception("네트워크 요청에 실패했습니다")
        }
    }

    suspend fun requestAccessToken(refreshToken: String): String {
        val response = userService.reissueAccessToken(RefreshTokenDTO(REFRESH_TOKEN))
        if (response.isSuccessful) {
            return response.body()?.toUserToken()?.accessToken ?: throw Exception("네트워크가 불안정합니다")
        } else {
            throw Exception("네트워크가 불안정합니다")
        }
    }

    suspend fun requestLogin(user: LoginUser): UserToken {
        val response = userService.postLogin(user.toLoginDTO())
        println("response $user ${response} ${response.body()}")
        if (response.isSuccessful) {
            return response.body()?.toUserToken() ?: throw Exception("존재하지 않는 사용자입니다")
        } else {
            throw Exception("존재하지 않는 사용자입니다")
        }
    }

    suspend fun requestRegister(registerUser: RegisterUser): UserToken {
        println("register request $registerUser")
        var request = registerUser.toRegisterDTO()
        registerUser.profileImageByteArray?.let {
            val url = getImageUrlByByteArray(registerUser.profileImageByteArray)
            request = request.copy(profileImageURL = url)
        }

        val response = userService.postSignUp(request)
        if (response.isSuccessful) {
            return response.body()?.toUserToken() ?: throw Exception(response.message())
        } else {
            throw Exception("중복된 닉네임입니다")
        }
    }

    suspend fun requestFollow(userId: String) {

    }

    suspend fun requestUnfollow(userId: String) {

    }

    private suspend fun getImageUrlByByteArray(imageByte: ByteArray): String {
        val requestFile = RequestBody.create(MediaType.parse("image/*"), imageByte);
        val uploadFile = MultipartBody.Part.createFormData("image", "", requestFile);
        val response = imageService.postImage(uploadFile)
        if (response.isSuccessful) {
            return response.body()?.imageUrl ?: throw Exception("이미지 업로드에 실패했습니다")
        } else {
            throw Exception("이미지 업로드에 실패했습니다")
        }
    }

}