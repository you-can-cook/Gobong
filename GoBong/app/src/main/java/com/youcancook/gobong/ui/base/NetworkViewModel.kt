package com.youcancook.gobong.ui.base

import com.youcancook.gobong.util.NetworkState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

open class NetworkViewModel : GoBongViewModel() {

    private val _networkState = MutableStateFlow<NetworkState>(NetworkState.DONE)
    val networkState: StateFlow<NetworkState> get() = _networkState


}