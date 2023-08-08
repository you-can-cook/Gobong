package com.youcancook.gobong.ui.addRecipe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.youcancook.gobong.databinding.BottomSheetRecipeSearchBinding

class RecipeStepSearchBottomFragment : Fragment() {

    private var _binding: BottomSheetRecipeSearchBinding? = null
    private val binding: BottomSheetRecipeSearchBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = BottomSheetRecipeSearchBinding.inflate(inflater, container, false)
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