package com.youcancook.gobong.ui.my.other

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.GridLayoutManager
import com.youcancook.gobong.R
import com.youcancook.gobong.adapter.GridItemDecorator
import com.youcancook.gobong.adapter.GridRecyclerViewListAdapter
import com.youcancook.gobong.databinding.ActivityOthersBinding
import com.youcancook.gobong.ui.base.NetworkActivity
import com.youcancook.gobong.ui.base.NetworkStateListener
import com.youcancook.gobong.ui.detail.DetailActivity
import com.youcancook.gobong.ui.my.OthersProfileViewModel
import com.youcancook.gobong.ui.my.follow.FollowActivity

class OthersActivity :
    NetworkActivity<ActivityOthersBinding, OthersProfileViewModel>(R.layout.activity_others) {
    override val viewModel: OthersProfileViewModel by lazy {
        OthersProfileViewModel(appContainer.goBongRepository, appContainer.userRepository)
    }
    private val gridAdapter = GridRecyclerViewListAdapter(3)

    private val gridItemDecorator =
        GridItemDecorator()

    override val onNetworkStateChange: NetworkStateListener = object : NetworkStateListener {
        override fun onSuccess() {
            binding.followingButton.isSelected = viewModel.isUserFollowed()
        }

        override fun onFail() {
        }

        override fun onDone() {
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = viewModel

        //TODO intent로 userId 받아오기
        viewModel.getOthersInfo("")

        binding.run {
            toolbar.setNavigationOnClickListener {
                if (it.isSelected) {
                    setGridRecyclerView()
                } else {
                    setLinearRecyclerView()
                }
                it.isSelected = it.isSelected.not()
            }

            setGridRecyclerView()

            followingButton.setOnClickListener {
                it.isSelected = it.isSelected.not()
                if (it.isSelected) {
                    viewModel.follow()
                    (it as Button).text = "팔로잉"
                } else {
                    viewModel.unfollow()
                    (it as Button).text = "팔로우"
                }
            }

            //TODO 다른 사람 팔로잉 목록도 볼 수 있도록
//            followerNumberTextView.setOnClickListener {
//                val intent = Intent(requireContext(), FollowActivity::class.java)
//                startActivity(intent)
//            }
//
//            //TODO 팔로잉 팔로워에 따라서 시작 위치 변경
//            followingNumberTextView.setOnClickListener {
//                val intent = Intent(requireContext(), FollowActivity::class.java)
//                startActivity(intent)
//            }
        }

    }

    private fun setGridRecyclerView() {
        binding.run {
            recyclerView.layoutManager = GridLayoutManager(this@OthersActivity, 3)
            gridAdapter.spanCount = 3
            recyclerView.removeItemDecoration(gridItemDecorator)
            recyclerView.addItemDecoration(gridItemDecorator.also { decorator ->
                decorator.margin =
                    resources.getDimension(R.dimen.grid_margin)
                        .toInt()
            })
            recyclerView.adapter = gridAdapter
        }
    }

    private fun setLinearRecyclerView() {
        binding.recyclerView.layoutManager = GridLayoutManager(this, 1)
        gridAdapter.spanCount = 1
        gridAdapter.notifyItemRangeChanged(0, gridAdapter.itemCount)
    }

}