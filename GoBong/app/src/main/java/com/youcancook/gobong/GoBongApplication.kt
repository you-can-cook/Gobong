package com.youcancook.gobong

import android.app.Application
import com.youcancook.gobong.di.GoBongContainer

class GoBongApplication : Application() {
    val container = GoBongContainer()
}