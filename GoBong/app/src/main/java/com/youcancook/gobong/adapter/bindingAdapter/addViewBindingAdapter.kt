package com.youcancook.gobong.adapter.bindingAdapter

import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import com.google.android.flexbox.FlexboxLayout
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.youcancook.gobong.R

@BindingAdapter("ingredientChips")
fun <T> ingredientChips(view: ChipGroup, data: List<String>) {

    //removeOldView
    view.removeAllViews()

    for (dt in data) {
        view.addView(Chip(view.context, null, R.attr.IngredientChips).apply {
            text = dt
        })
    }
}

@BindingAdapter("recipeStepTools")
fun <T> recipeStepTools(view: ChipGroup, data: List<String>) {

    //removeOldView
    val childCount = view.childCount
    view.removeViews(1, childCount - 1)

    for (dt in data) {
        view.addView(Chip(view.context, null, R.attr.RecipeStepTools).apply {
            text = dt
        })
    }
}


fun FlexboxLayout.addIngredient(data: String) {

    val dp = resources.displayMetrics.density.toInt()
    val ViewGrouplayoutParams = FlexboxLayout.LayoutParams(
        ViewGroup.LayoutParams.WRAP_CONTENT,
        ViewGroup.LayoutParams.WRAP_CONTENT
    )
    ViewGrouplayoutParams.setMargins(0, 0, 4 * dp, 0)

    addView(Chip(context, null, R.attr.IngredientAddedChips).apply {
        text = data
        layoutParams = ViewGrouplayoutParams
        setOnCloseIconClickListener {
            this@addIngredient.removeView(it)
        }
    }, childCount - 2)
}

