package com.youcancook.gobong.ui.detail

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.youcancook.gobong.adapter.RecipeListAdapter
import com.youcancook.gobong.databinding.ActivityDetailBinding
import com.youcancook.gobong.databinding.DialogReviewBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel: DetailViewModel by viewModels()
    private val recipeAdapter = RecipeListAdapter(onItemClick = {
        detailViewModel.activeRecipeStep(it)
    })
    private val reviewDialog = ReviewDialogFragment(onDismissListener = {
        detailViewModel.setStar(it)
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.run {
            vm = detailViewModel
            lifecycleOwner = this@DetailActivity
            recyclerView.adapter = recipeAdapter
        }

        binding.run {
            toolbar.setNavigationOnClickListener {
                finish()
            }

            reviewButton.setOnClickListener {
                reviewDialog.setOldStar(detailViewModel.getStar())
                reviewDialog.show(supportFragmentManager, null)
            }

        }

        binding.run {
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    detailViewModel.starCount.collectLatest {
                        if (it != 0) {
                            reviewButton.text = "리뷰 수정하기"
                        }
                    }
                }

            }
        }
    }


}