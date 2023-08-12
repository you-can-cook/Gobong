package com.youcancook.gobong.model.repository

import com.youcancook.gobong.model.datasource.UserDataSource

class UserRepository(
    private val userDataSource: UserDataSource,
) {

    suspend fun getTemporaryToken(): String {
        return ""
    }

    suspend fun login() {

    }

    suspend fun register() {

    }
}