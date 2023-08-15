package com.youcancook.gobong.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.youcancook.gobong.R
import com.youcancook.gobong.adapter.CardRecyclerViewListAdapter
import com.youcancook.gobong.databinding.FragmentHomeBinding
import com.youcancook.gobong.ui.addRecipe.AddRecipeActivity
import com.youcancook.gobong.ui.base.NetworkFragment
import com.youcancook.gobong.ui.base.NetworkStateListener
import com.youcancook.gobong.ui.detail.DetailActivity

class HomeFragment : NetworkFragment<FragmentHomeBinding, HomeViewModel>(R.layout.fragment_home) {
    override val viewModel: HomeViewModel by lazy {
        HomeViewModel(appContainer.goBongRepository, appContainer.userRepository)
    }

    private val cardAdapter = CardRecyclerViewListAdapter(onItemClick = {
        val intent = Intent(requireContext(), DetailActivity::class.java)
        startActivity(intent)
    }, onFollowClick = {
        if (it.followed) {
            viewModel.unfollow(it.nickname)
        } else {
            viewModel.follow(it.nickname)
        }
    })

    override val onNetworkStateChange: NetworkStateListener = object : NetworkStateListener {
        override fun onSuccess() {
            binding.swipeRefresh.isRefreshing = false
        }

        override fun onFail() {
            binding.swipeRefresh.isRefreshing = false
        }

        override fun onDone() {
            binding.swipeRefresh.isRefreshing = false
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.run {
            vm = viewModel
        }

        super.onViewCreated(view, savedInstanceState)

        binding.run {
            recyclerView.adapter = cardAdapter
            addRecipeButton.setOnClickListener {
                val intent = Intent(
                    requireContext(), AddRecipeActivity::class.java
                )
                startActivity(intent)
            }

            swipeRefresh.setOnRefreshListener {
                viewModel.getFollowingRecipes()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.getFollowingRecipes()
    }

}