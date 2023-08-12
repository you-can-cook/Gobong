package com.youcancook.gobong.ui.my.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.youcancook.gobong.R
import com.youcancook.gobong.databinding.FragmentSettingBinding

class SettingFragment : Fragment() {

    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSettingBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.run {
            backArrowButton.setOnClickListener {
                requireActivity().finish()
            }

            myAccountMoreImageView.setOnClickListener {
                findNavController().navigate(R.id.action_settingFragment_to_settingProfileFragment)
            }

            alarmMoreImageView.setOnClickListener {
                findNavController().navigate(R.id.action_settingFragment_to_settingNotificationFragment)
            }

            quitMoreImageView.setOnClickListener {
                findNavController().navigate(R.id.action_settingFragment_to_settingQuitFragment)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}