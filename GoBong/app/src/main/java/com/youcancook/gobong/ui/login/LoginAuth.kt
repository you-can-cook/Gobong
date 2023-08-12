package com.youcancook.gobong.ui.login

import java.io.Serializable

data class LoginAuth(
    val provider: String,
    val oauthId: String,
    val temporaryToken: String,
) : Serializable