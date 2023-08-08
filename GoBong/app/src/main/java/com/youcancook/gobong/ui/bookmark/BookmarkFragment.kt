package com.youcancook.gobong.ui.bookmark

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
import com.youcancook.gobong.databinding.FragmentBookmarkBinding
import com.youcancook.gobong.model.Card

class BookmarkFragment : Fragment() {

    private var _binding: FragmentBookmarkBinding? = null
    private val binding get() = _binding!!

    private val gridAdapter = GridRecyclerViewListAdapter()
    private val gridItemDecorator =
        GridItemDecorator()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this).get(BookmarkViewModel::class.java)

        _binding = FragmentBookmarkBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.run {
            recyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
            recyclerView.addItemDecoration(gridItemDecorator.also {
                it.halfMargin =
                    requireContext().resources.getDimension(R.dimen.grid_half_margin).toInt()
            })
            recyclerView.adapter = gridAdapter
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
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}