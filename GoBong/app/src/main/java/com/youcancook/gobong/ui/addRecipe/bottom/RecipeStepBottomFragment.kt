package com.youcancook.gobong.ui.addRecipe.bottom

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.youcancook.gobong.R
import com.youcancook.gobong.databinding.BottomSheetRecipeStepBinding
import com.youcancook.gobong.model.Recipe
import com.youcancook.gobong.model.RecipeStepAdded
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class RecipeStepBottomFragment : BottomSheetDialogFragment() {
    private lateinit var binding: BottomSheetRecipeStepBinding
    private val recipeAddBottomViewModel: RecipeStepBottomViewModel by activityViewModels()
    private var onDismissListener: (Recipe) -> Unit? = { }

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

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                recipeAddBottomViewModel.isSavedSuccess.collectLatest {
                    if (it) {
                        dismiss()
                        recipeAddBottomViewModel.resetSave()
                    }
                }
            }
        }
    }

    fun setOldRecipe(recipe: RecipeStepAdded) {
        recipeAddBottomViewModel.setOldRecipe(recipe)
    }

    fun setOnDismissListener(listener: (Recipe) -> Unit) {
        onDismissListener = listener
    }

    override fun onDismiss(dialog: DialogInterface) {
        onDismissListener(recipeAddBottomViewModel.getNewRecipeStep())
        super.onDismiss(dialog)
    }

    companion object {
        const val TAG = "RecipeStepBottomFragment"
    }
}