package com.youcancook.gobong.ui.bookmark

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.youcancook.gobong.R
import com.youcancook.gobong.adapter.GridItemDecorator
import com.youcancook.gobong.adapter.GridRecyclerViewListAdapter
import com.youcancook.gobong.databinding.FragmentBookmarkBinding
import com.youcancook.gobong.model.Card

class BookmarkFragment : Fragment() {

    private var _binding: FragmentBookmarkBinding? = null
    private val binding get() = _binding!!

    private val gridAdapter = GridRecyclerViewListAdapter(3,onItemClick = {

    })
    private val gridItemDecorator =
        GridItemDecorator()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentBookmarkBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.run {
            recyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
            recyclerView.addItemDecoration(gridItemDecorator.also {
                it.margin =
                    requireContext().resources.getDimension(R.dimen.grid_margin).toInt()
            })
            recyclerView.adapter = gridAdapter
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