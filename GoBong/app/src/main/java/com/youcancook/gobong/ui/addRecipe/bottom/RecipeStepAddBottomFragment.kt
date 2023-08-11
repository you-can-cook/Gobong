package com.youcancook.gobong.ui.addRecipe.bottom

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.chip.Chip
import com.google.android.material.snackbar.Snackbar
import com.youcancook.gobong.R
import com.youcancook.gobong.databinding.BottomSheetRecipeStepAddBinding
import com.youcancook.gobong.ui.ImageActivity
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class RecipeStepAddBottomFragment : Fragment() {
    private var _binding: BottomSheetRecipeStepAddBinding? = null
    private val binding: BottomSheetRecipeStepAddBinding get() = _binding!!
    private val recipeAddBottomViewModel: RecipeStepBottomViewModel by activityViewModels()
    private var imagePickActivityLauncher: ActivityResultLauncher<Intent>? = null


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

        imagePickActivityLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    val imageData = result.data?.getByteArrayExtra("imageData")
                    imageData ?: return@registerForActivityResult
                    recipeAddBottomViewModel.setThumbnailByteArray(imageData)
                }
            }

        binding.run {
            vm = recipeAddBottomViewModel
            lifecycleOwner = viewLifecycleOwner

            photoImageView.setOnClickListener {
                getImageFromGallery()
            }

            toolsLayout.setOnCheckedStateChangeListener { group, checkedIds ->
                val views = checkedIds.map {
                    group.findViewById<Chip>(it).text.toString()
                }
                recipeAddBottomViewModel.checkTools(views)
            }

            showMoreToolsButton.setOnClickListener {
                parentFragmentManager.beginTransaction()
                    .add(R.id.bottomSheetFragment, RecipeStepSearchBottomFragment())
                    .addToBackStack(null)
                    .commit()
            }

            tenSecondChip.setOnClickListener {
                recipeAddBottomViewModel.addSecond(10)
            }

            thirtySecondChip.setOnClickListener {
                recipeAddBottomViewModel.addSecond(30)
            }

            oneMinuteChip.setOnClickListener {
                recipeAddBottomViewModel.addMinute(1)
            }

            fiveMinuteChip.setOnClickListener {
                recipeAddBottomViewModel.addMinute(5)

            }

            tenMinuteChip.setOnClickListener {
                recipeAddBottomViewModel.addMinute(10)
            }

            resetTimeImageView.setOnClickListener {
                recipeAddBottomViewModel.clearTime()
            }

            completeButton.setOnClickListener {
                recipeAddBottomViewModel.saveRecipeStep()
            }
        }

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                recipeAddBottomViewModel.snackBarMessage.collectLatest {
                    if (it.isNotEmpty()) {
                        Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun getImageFromGallery() {
        Intent(requireActivity(), ImageActivity::class.java).run {
            imagePickActivityLauncher?.launch(this)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}