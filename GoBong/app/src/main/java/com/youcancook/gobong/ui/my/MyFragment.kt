package com.youcancook.gobong.ui.my

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.youcancook.gobong.R
import com.youcancook.gobong.adapter.GridItemDecorator
import com.youcancook.gobong.adapter.GridRecyclerViewListAdapter
import com.youcancook.gobong.databinding.FragmentMyBinding
import com.youcancook.gobong.model.Card
import com.youcancook.gobong.ui.detail.DetailActivity
import com.youcancook.gobong.ui.my.follow.FollowActivity
import com.youcancook.gobong.ui.my.setting.SettingActivity

class MyFragment : Fragment() {

    private var _binding: FragmentMyBinding? = null
    private val binding get() = _binding!!

    private val myViewModel by lazy {
        ViewModelProvider(this).get(MyViewModel::class.java)
    }
    private val gridAdapter = GridRecyclerViewListAdapter(3, onItemClick = {
        val intent = Intent(requireContext(), DetailActivity::class.java)
        startActivity(intent)
    })

    private val gridItemDecorator =
        GridItemDecorator()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentMyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        gridItemDecorator.also {
            it.margin =
                requireContext().resources.getDimension(R.dimen.grid_margin).toInt()
        }

        binding.run {
            vm = myViewModel
            lifecycleOwner = this@MyFragment.viewLifecycleOwner
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

        gridAdapter.submitList(
            listOf(
                Card(),
                Card(),
                Card(),
                Card(),
                Card(),
                Card(),
                Card()
            )
        )

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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}