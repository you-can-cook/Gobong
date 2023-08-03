package com.youcancook.gobong.adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
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

