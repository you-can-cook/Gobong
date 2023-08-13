package com.youcancook.gobong.ui.search

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.youcancook.gobong.R
import com.youcancook.gobong.adapter.GridItemDecorator
import com.youcancook.gobong.adapter.GridRecyclerViewListAdapter
import com.youcancook.gobong.databinding.FragmentSearchBinding
import com.youcancook.gobong.ui.base.NetworkFragment
import com.youcancook.gobong.ui.base.NetworkStateListener
import com.youcancook.gobong.ui.detail.DetailActivity
import com.youcancook.gobong.ui.search.filter.FilterActivity

class SearchFragment :
    NetworkFragment<FragmentSearchBinding, SearchViewModel>(R.layout.fragment_search) {

    override val viewModel: SearchViewModel by lazy {
        SearchViewModel(appContainer.goBongRepository)
    }

    private val gridAdapter = GridRecyclerViewListAdapter(3, onItemClick = {
        val intent = Intent(requireContext(), DetailActivity::class.java)
        startActivity(intent)
    })
    private val gridItemDecorator = GridItemDecorator()
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

            toggleButton.setOnClickListener {
                if (it.isSelected) {
                    setGridRecyclerView()
                } else {
                    setLinearRecyclerView()
                }
                it.isSelected = it.isSelected.not()
            }

            filterButton.setOnClickListener {
                val intent = Intent(requireContext(), FilterActivity::class.java)
                startActivity(intent)
            }

            swipeRefresh.setOnRefreshListener {
                viewModel.getAllRecipes()
            }
            setGridRecyclerView()
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.getAllRecipes()
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