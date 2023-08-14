package com.youcancook.gobong.ui.my.follow

import com.youcancook.gobong.model.User
import com.youcancook.gobong.model.repository.UserRepository
import com.youcancook.gobong.ui.base.NetworkViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FollowViewModel(
    private val userRepository: UserRepository,
) : NetworkViewModel() {

    private val _followers = MutableStateFlow<List<User>>(emptyList())
    val followers: StateFlow<List<User>> get() = _followers

    private val _followings = MutableStateFlow<List<User>>(emptyList())
    val followings: StateFlow<List<User>> get() = _followings

}