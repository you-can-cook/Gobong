package com.youcancook.gobong.ui.search

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.youcancook.gobong.R
import com.youcancook.gobong.adapter.CardRecyclerViewListAdapter
import com.youcancook.gobong.adapter.GridItemDecorator
import com.youcancook.gobong.adapter.GridRecyclerViewListAdapter
import com.youcancook.gobong.databinding.FragmentSearchBinding
import com.youcancook.gobong.model.Card
import com.youcancook.gobong.ui.detail.DetailActivity

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val searchViewModel: SearchViewModel by viewModels()

    private val gridAdapter = GridRecyclerViewListAdapter(3, onItemClick = {
        val intent = Intent(requireContext(), DetailActivity::class.java)
        startActivity(intent)
    })
    private val gridItemDecorator = GridItemDecorator()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.run {
            vm = searchViewModel
            lifecycleOwner = viewLifecycleOwner
        }

        binding.run {

            toggleButton.setOnClickListener {
                if (it.isSelected) {
                    setGridRecyclerView()
                } else {
                    setLinearRecyclerView()
                }
                it.isSelected = it.isSelected.not()
            }

            setGridRecyclerView()
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}