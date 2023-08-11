package com.youcancook.gobong.ui.my

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.youcancook.gobong.R
import com.youcancook.gobong.adapter.GridItemDecorator
import com.youcancook.gobong.adapter.GridRecyclerViewListAdapter
import com.youcancook.gobong.databinding.FragmentMyBinding
import com.youcancook.gobong.ui.base.NetworkFragment
import com.youcancook.gobong.ui.detail.DetailActivity
import com.youcancook.gobong.ui.my.follow.FollowActivity
import com.youcancook.gobong.ui.my.setting.SettingActivity

class MyFragment : NetworkFragment<FragmentMyBinding, MyViewModel>(R.layout.fragment_my) {

    override val viewModel: MyViewModel by viewModels()

    private val gridAdapter = GridRecyclerViewListAdapter(3, onItemClick = {
        val intent = Intent(requireContext(), DetailActivity::class.java)
        startActivity(intent)
    })

    private val gridItemDecorator =
        GridItemDecorator()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.run {
            vm = viewModel
        }

        super.onViewCreated(view, savedInstanceState)

        gridItemDecorator.also {
            it.margin =
                requireContext().resources.getDimension(R.dimen.grid_margin).toInt()
        }

        binding.run {
            recyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
            recyclerView.addItemDecoration(gridItemDecorator)
            recyclerView.adapter = gridAdapter
        }

        binding.run {

            toolbar.setNavigationOnClickListener {
                if (it.isSelected) {
                    setGridRecyclerView()
                } else {
                    setLinearRecyclerView()
                }
                it.isSelected = it.isSelected.not()
            }

            toolbar.setOnMenuItemClickListener {
                if (it.itemId == R.id.toolbar_my_setting) {
                    val intent = Intent(requireContext(), SettingActivity::class.java)
                    startActivity(intent)
                }
                return@setOnMenuItemClickListener true
            }

            setGridRecyclerView()

            followerNumberTextView.setOnClickListener {
                val intent = Intent(requireContext(), FollowActivity::class.java)
                startActivity(intent)
            }

            //TODO 팔로잉 팔로워에 따라서 시작 위치 변경
            followingNumberTextView.setOnClickListener {
                val intent = Intent(requireContext(), FollowActivity::class.java)
                startActivity(intent)
            }
        }

    }

    private fun setGridRecyclerView() {
        binding.run {
            recyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
            gridAdapter.spanCount = 3
            recyclerView.removeItemDecoration(gridItemDecorator)
            recyclerView.addItemDecoration(gridItemDecorator.also { decorator ->
                decorator.margin =
                    requireContext().resources.getDimension(R.dimen.grid_margin)
                        .toInt()
            })
            recyclerView.adapter = gridAdapter
        }
    }

    private fun setLinearRecyclerView() {
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 1)
        gridAdapter.spanCount = 1
        gridAdapter.notifyItemRangeChanged(0, gridAdapter.itemCount)
    }

}