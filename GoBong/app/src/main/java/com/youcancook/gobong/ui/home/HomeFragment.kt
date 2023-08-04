package com.youcancook.gobong.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.youcancook.gobong.adapter.CardRecyclerViewListAdapter
import com.youcancook.gobong.databinding.FragmentHomeBinding
import com.youcancook.gobong.model.Recipe
import com.youcancook.gobong.ui.detail.DetailActivity

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val cardAdapter = CardRecyclerViewListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.run {
            recyclerView.adapter = cardAdapter
            addRecipeButton.setOnClickListener {
                val intent = Intent(requireContext(), DetailActivity::class.java)
                startActivity(intent)

            }
        }

        cardAdapter.submitList(
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