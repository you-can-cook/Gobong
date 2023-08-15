package com.youcancook.gobong.ui.my

import androidx.lifecycle.viewModelScope
import com.youcancook.gobong.model.Card
import com.youcancook.gobong.model.User
import com.youcancook.gobong.model.repository.GoBongRepositoryImpl
import com.youcancook.gobong.util.NetworkState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MyProfileViewModel(
    private val goBongRepository: GoBongRepositoryImpl,
) : ProfileViewModel(goBongRepository) {

    fun getMyInfo() {
        viewModelScope.launch {
            setNetworkState(NetworkState.LOADING)
            try {
                requestMyInfo()
                setNetworkState(NetworkState.SUCCESS)
            } catch (e: Exception) {
                setNetworkState(NetworkState.FAIL)
                setSnackBarMessage(e.message ?: "")
            }
            finishNetwork()
        }
    }

    private fun requestMyInfo() {

    }
}