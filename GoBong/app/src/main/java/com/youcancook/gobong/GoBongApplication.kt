package com.youcancook.gobong

import android.app.Application
import android.util.Log
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.util.Utility
import com.youcancook.gobong.di.GoBongContainer
import com.youcancook.gobong.util.NATIVE_APP_KEY

class GoBongApplication : Application() {
    val container = GoBongContainer()

    override fun onCreate() {
        super.onCreate()

        KakaoSdk.init(this, NATIVE_APP_KEY)
        val keyHash = Utility.getKeyHash(this)
        Log.d("Hash", keyHash)
    }
}