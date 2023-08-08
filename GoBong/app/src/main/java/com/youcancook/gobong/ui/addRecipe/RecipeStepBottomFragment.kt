package com.youcancook.gobong.ui.addRecipe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.youcancook.gobong.R
import com.youcancook.gobong.databinding.BottomSheetRecipeStepBinding

class RecipeStepBottomFragment : BottomSheetDialogFragment() {
    private lateinit var binding: BottomSheetRecipeStepBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = BottomSheetRecipeStepBinding.inflate(inflater, container, false)
        childFragmentManager.beginTransaction().replace(
            R.id.bottomSheetFragment,
            RecipeStepAddBottomFragment()
        ).commit()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    companion object {
        const val TAG = "RecipeStepBottomFragment"
    }
}