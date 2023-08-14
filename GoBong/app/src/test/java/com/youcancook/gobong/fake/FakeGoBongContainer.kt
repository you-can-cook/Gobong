package com.youcancook.gobong.fake

import com.youcancook.gobong.model.repository.GoBongRepositoryImpl
import com.youcancook.gobong.model.repository.UserRepositoryImpl

class FakeGoBongContainer {

    val goBongRepository = FakeGoBongRepository()
    val userRepository = FakeUserRepository()
}