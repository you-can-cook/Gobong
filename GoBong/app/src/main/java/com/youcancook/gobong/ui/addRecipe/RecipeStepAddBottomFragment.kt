package com.youcancook.gobong.ui.addRecipe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.youcancook.gobong.databinding.BottomSheetRecipeStepAddBinding

class RecipeStepAddBottomFragment : Fragment() {
    private var _binding: BottomSheetRecipeStepAddBinding? = null
    private val binding: BottomSheetRecipeStepAddBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = BottomSheetRecipeStepAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}