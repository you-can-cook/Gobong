package com.youcancook.gobong.ui.base

import com.youcancook.gobong.util.NetworkState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

open class NetworkViewModel : GoBongViewModel() {

    private val _networkState = MutableStateFlow(NetworkState.DONE)
    val networkState: StateFlow<NetworkState> get() = _networkState

    protected fun setNetworkState(state: NetworkState) {
        _networkState.value = state
    }

    protected fun finishNetwork() {
        if (_networkState.value == NetworkState.LOADING) {
            _networkState.value = NetworkState.DONE
        }
    }

}