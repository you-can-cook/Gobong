package com.youcancook.gobong.ui.my.follow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.youcancook.gobong.adapter.FollowListAdapter
import com.youcancook.gobong.databinding.FragmentFollowingBinding
import com.youcancook.gobong.model.User

class FollowingFragment : Fragment() {

    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding!!
    private val followAdapter = FollowListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentFollowingBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.run {
            recyclerView.adapter = followAdapter
        }

        followAdapter.submitList(
            listOf(
                User(),
                User(),
                User(),
                User(),
                User(),
                User(),
                User(),
                User(),

                )
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}