package com.youcancook.gobong.ui.my

import androidx.lifecycle.viewModelScope
import com.youcancook.gobong.model.User
import com.youcancook.gobong.model.repository.GoBongRepository
import com.youcancook.gobong.model.repository.UserRepository
import com.youcancook.gobong.util.NetworkState
import kotlinx.coroutines.launch

class OthersProfileViewModel(
    private val goBongRepository: GoBongRepository,
    private val userRepository: UserRepository,
) : ProfileViewModel(goBongRepository) {
    fun isUserFollowed(): Boolean {
        return getUserInfo().followed
    }

    fun getOthersInfo(userId: String) {
        viewModelScope.launch {
            setNetworkState(NetworkState.LOADING)
            try {
                requestOthersInfo(userId)
                setNetworkState(NetworkState.SUCCESS)
            } catch (e: Exception) {
                setNetworkState(NetworkState.FAIL)
                setSnackBarMessage(e.message ?: "")
            }
        }
    }

    private fun setOthersInfo(user: User) {
        setUserInfo(user)
    }

    private suspend fun requestOthersInfo(userId: String) {
//        val response = goBongRepository.getUserRecipes(userId)
//        setUserInfo(response)
//        setUserRecipe(response.recipes)
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