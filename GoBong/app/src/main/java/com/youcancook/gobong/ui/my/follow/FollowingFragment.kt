package com.youcancook.gobong.ui.my.follow

import android.os.Bundle
import android.view.View
import com.youcancook.gobong.R
import com.youcancook.gobong.adapter.FollowListAdapter
import com.youcancook.gobong.databinding.FragmentFollowingBinding
import com.youcancook.gobong.ui.base.NetworkFragment
import com.youcancook.gobong.ui.base.NetworkStateListener

class FollowingFragment :
    NetworkFragment<FragmentFollowingBinding, FollowViewModel>(R.layout.fragment_following) {
    override val viewModel: FollowViewModel by lazy {
        FollowViewModel(appContainer.userRepository)
    }

    override val onNetworkStateChange: NetworkStateListener = object : NetworkStateListener {
        override fun onSuccess() {
        }

        override fun onFail() {
        }

        override fun onDone() {
        }

    }

    private val followAdapter = FollowListAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.vm = viewModel
        super.onViewCreated(view, savedInstanceState)
        binding.run {
            recyclerView.adapter = followAdapter
        }
    }

}