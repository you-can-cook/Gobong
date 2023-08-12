package com.youcancook.gobong.model.datasource

import com.youcancook.gobong.model.network.UserService

class UserDataSource(
    private val userService: UserService,
) {
    suspend fun requestTemporaryToken() {

    }

    suspend fun requestLogin() {

    }

    suspend fun requestRegister() {

    }
}