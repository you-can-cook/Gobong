package com.youcancook.gobong.ui.search.filter

import com.youcancook.gobong.ui.addRecipe.bottom.add.RecipeStepAddBottomFragment
import com.youcancook.gobong.ui.addRecipe.bottom.add.RecipeStepAddBottomViewModel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.youcancook.gobong.R
import com.youcancook.gobong.databinding.BottomSheetFrameBinding

class FilterToolBottomSheet : BottomSheetDialogFragment() {
    private var _binding: BottomSheetFrameBinding? = null
    private val binding: BottomSheetFrameBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = BottomSheetFrameBinding.inflate(inflater, container, false)
        childFragmentManager.beginTransaction().replace(
            R.id.bottomSheetFragment,
            FilterToolBottomFragment()
        ).commit()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "FilterBottomSheet"
    }
}