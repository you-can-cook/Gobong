package com.youcancook.gobong.adapter.bindingAdapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView


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

@BindingAdapter("isSelected")
fun <T> isSelected(view: View, selected: Boolean) {
    view.isSelected = selected
}
