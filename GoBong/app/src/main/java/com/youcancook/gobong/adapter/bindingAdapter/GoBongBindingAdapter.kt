package com.youcancook.gobong.adapter.bindingAdapter

import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
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
        .into(view)
}

@BindingAdapter("setImageUrlWithEmpty")
fun setImageUrlWithEmpty(view: ImageView, url: String) {
    Glide.with(view.context)
        .load(url)
        .into(view)
}

@BindingAdapter("setImageByteArray")
fun setImageByteArray(view: ImageView, image: ByteArray?) {
    Glide.with(view.context)
        .load(image)
        .placeholder(R.drawable.thumbnail_add_placeholder)
        .into(view)
}

@BindingAdapter("setImageByteArrayWithEmpty")
fun setImageByteArrayWithEmpty(view: ImageView, image: ByteArray?) {
    Glide.with(view.context)
        .load(image)
        .into(view)
}

@BindingAdapter("setNecessaryTextView")
fun setNecessaryTextView(view: TextView, text: String) {
    val wordToSpan: Spannable =
        SpannableString(text)

    wordToSpan.setSpan(
        ForegroundColorSpan(view.context.resources.getColor(R.color.red)),
        text.length - 1,
        text.length,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )

    view.text = wordToSpan
}


@BindingAdapter("submitData")
fun <T> submitData(view: RecyclerView, data: List<T>?) {
    println("submitDatat $data")
    data ?: return

    val adapter = view.adapter as ListAdapter<T, RecyclerView.ViewHolder?>
    adapter.submitList(data)
}


