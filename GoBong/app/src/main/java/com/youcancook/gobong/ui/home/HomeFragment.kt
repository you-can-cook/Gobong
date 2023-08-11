package com.youcancook.gobong.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.youcancook.gobong.BR
import com.youcancook.gobong.R
import com.youcancook.gobong.adapter.CardRecyclerViewListAdapter
import com.youcancook.gobong.databinding.FragmentHomeBinding
import com.youcancook.gobong.ui.addRecipe.AddRecipeActivity
import com.youcancook.gobong.ui.base.NetworkFragment
import com.youcancook.gobong.ui.detail.DetailActivity

class HomeFragment : NetworkFragment<FragmentHomeBinding, HomeViewModel>(R.layout.fragment_home) {
    override val viewModel: HomeViewModel by viewModels()

    private val cardAdapter = CardRecyclerViewListAdapter(onItemClick = {
        val intent = Intent(requireContext(), DetailActivity::class.java)
        startActivity(intent)
    })

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
        }

    }

}