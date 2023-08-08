package com.youcancook.gobong.ui.detail

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.youcancook.gobong.adapter.RecipeListAdapter
import com.youcancook.gobong.databinding.ActivityDetailBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel: DetailViewModel by viewModels()
    private val recipeAdapter = RecipeListAdapter(onItemClick = {
        detailViewModel.activeRecipeStep(it)
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
            val stars = listOf(star1, star2, star3, star4, star5)

            for ((index, star) in stars.withIndex()) {
                star.setOnClickListener {
                    detailViewModel.setStar(index + 1)
                }
            }

            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    detailViewModel.starCount.collectLatest {
                        stars.forEach { it2 -> it2.isSelected = false }

                        for (i in 0 until it) {
                            stars[i].isSelected = true
                        }
                    }
                }

            }

        }

        binding.run {
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    detailViewModel.activeRecipeStep.collectLatest {

                    }
                }

            }
        }
    }

}