package com.youcancook.gobong.ui.my

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
import com.youcancook.gobong.model.Recipe

class MyFragment : Fragment() {

    private var _binding: FragmentMyBinding? = null
    private val binding get() = _binding!!

    private val myViewModel by lazy{
        ViewModelProvider(this).get(MyViewModel::class.java)
    }
    private val gridAdapter = GridRecyclerViewListAdapter()
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

        binding.run{
            vm = myViewModel
            lifecycleOwner = this@MyFragment.viewLifecycleOwner
        }

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
                Recipe(),
                Recipe(),
                Recipe(),
                Recipe(),
                Recipe(),
                Recipe(),
                Recipe()
            )
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}