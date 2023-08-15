package com.youcancook.gobong.ui.my

import androidx.lifecycle.viewModelScope
import com.youcancook.gobong.model.User
import com.youcancook.gobong.model.repository.GoBongRepository
import com.youcancook.gobong.model.repository.GoBongRepositoryImpl
import com.youcancook.gobong.model.repository.UserRepository
import com.youcancook.gobong.model.repository.UserRepositoryImpl
import com.youcancook.gobong.util.NetworkState
import kotlinx.coroutines.launch

class OthersProfileViewModel(
    private val goBongRepository: GoBongRepositoryImpl,
    private val userRepository: UserRepositoryImpl,
) : ProfileViewModel(goBongRepository) {

    fun isUserFollowed(): Boolean {
        return getUserInfo().followed
    }

    fun getOthersInfo(userId: String) {
        viewModelScope.launch {
            setNetworkState(NetworkState.LOADING)
            try {
                requestOthersInfo()
                setNetworkState(NetworkState.SUCCESS)
            } catch (e: Exception) {
                setNetworkState(NetworkState.FAIL)
                setSnackBarMessage(e.message ?: "")
            }
            finishNetwork()
        }
    }

    private fun setOthersInfo(user: User) {
        setUserInfo(user)
    }

    private suspend fun requestOthersInfo() {
        setUserInfo(User())
    }

    fun follow() {
        viewModelScope.launch {
            setNetworkState(NetworkState.LOADING)
            try {
                requestFollow()
                setNetworkState(NetworkState.SUCCESS)
            } catch (e: Exception) {
                setNetworkState(NetworkState.FAIL)
                setSnackBarMessage(e.message ?: "")
            }
        }
    }

    fun unfollow() {
        viewModelScope.launch {
            setNetworkState(NetworkState.LOADING)
            try {
                requestUnfollow()
                setNetworkState(NetworkState.SUCCESS)
            } catch (e: Exception) {
                setNetworkState(NetworkState.FAIL)
                setSnackBarMessage(e.message ?: "")
            }
        }
    }

    private suspend fun requestFollow() {
        userRepository.follow(getUserInfo().userId)
        setOthersInfo(getUserInfo().copy(followed = true))
    }

    private suspend fun requestUnfollow() {
        userRepository.unfollow(getUserInfo().userId)
        setOthersInfo(getUserInfo().copy(followed = false))
    }
}