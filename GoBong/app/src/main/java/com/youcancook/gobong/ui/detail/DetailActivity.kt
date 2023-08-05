package com.youcancook.gobong.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.youcancook.gobong.adapter.RecipeListAdapter
import com.youcancook.gobong.databinding.ActivityDetailBinding
import com.youcancook.gobong.model.RecipeStep

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val recipeAdapter = RecipeListAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.run{
            recyclerView.adapter = recipeAdapter
        }

        recipeAdapter.submitList(listOf(
            RecipeStep(),
            RecipeStep(),
            RecipeStep()
        ))
    }

}