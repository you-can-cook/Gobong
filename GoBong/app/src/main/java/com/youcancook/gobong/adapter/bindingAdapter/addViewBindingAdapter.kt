package com.youcancook.gobong.adapter.bindingAdapter

import androidx.databinding.BindingAdapter
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.youcancook.gobong.R

@BindingAdapter("ingredientChips")
fun <T> ingredientChips(view: ChipGroup, data: List<String>) {

    for (dt in data) {
        println("chipData $dt")
        view.addView(Chip(view.context, null, R.attr.IngredientChips).apply {
            text = dt
        })
    }
}

@BindingAdapter("recipeStepTools")
fun <T> recipeStepTools(view: ChipGroup, data: List<String>) {

    for (dt in data) {
        view.addView(Chip(view.context, null, R.attr.RecipeStepTools).apply {
            text = dt
        })
    }
}


