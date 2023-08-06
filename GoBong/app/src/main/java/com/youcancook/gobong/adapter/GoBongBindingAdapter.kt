package com.youcancook.gobong.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.youcancook.gobong.R

@BindingAdapter("setProfileImageUrl")
fun setProfileImageUrl(view: ImageView, url: String) {
    Glide.with(view.context)
        .load(url)
        .placeholder(R.drawable.default_profile_img)
        .circleCrop()
        .into(view)
}

@BindingAdapter("setImageUrl")
fun setImageUrl(view: ImageView, url: String) {
    Glide.with(view.context)
        .load(url)
        .placeholder(R.drawable.example_img)
        .circleCrop()
        .into(view)
}

@BindingAdapter("setImageVisible")
fun setImageVisible(view: ImageView, visible: Boolean) {
    view.isVisible = visible
}

@BindingAdapter("setTextVisible")
fun setTextVisible(view: TextView, text: String) {
    view.isVisible = text.isNotEmpty()
}


@BindingAdapter("setEmptyTextVisible")
fun <T> setEmptyTextVisible(view: TextView, data: List<T>) {
    view.isVisible = data.isEmpty()
}

@BindingAdapter("setRecyclerViewVisible")
fun <T> setRecyclerViewVisible(view: RecyclerView, data: List<T>) {
    view.isVisible = data.isNotEmpty()
}

@BindingAdapter("isActivated")
fun <T> isActivated(view: View, activated: Boolean) {
    view.isActivated = activated
}


@BindingAdapter("submitData")
fun <T> submitData(view: RecyclerView, data: List<T>?) {
    data ?: return

    val adapter = view.adapter as ListAdapter<T, RecyclerView.ViewHolder?>
    adapter.submitList(data)
}

@BindingAdapter("ingredientChips")
fun <T> ingredientChips(view: ChipGroup, data: List<String>) {

    for (dt in data) {
        view.addView(Chip(view.context, null, R.attr.IngredientChips).apply {
            text = dt
        })
    }
}


