package com.youcancook.gobong.adapter

import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.youcancook.gobong.databinding.ItemMainCardBinding
import com.youcancook.gobong.model.Card
import com.youcancook.gobong.model.UserProfile

class CardViewHolder(
    val binding: ItemMainCardBinding,
    val adapter: ListAdapter<*, *>,
    val onItemClick: (Card) -> Unit,
    val onFollowClick: (UserProfile) -> Unit = {},
) :
    RecyclerView.ViewHolder(binding.root) {

    init {
        binding.run {
            followingButton.setOnClickListener {
                onFollowClick((adapter.currentList[adapterPosition] as Card).user)
            }
            thumbnailImageView.setOnClickListener {
                onItemClick(adapter.currentList[adapterPosition] as Card)
            }
        }
    }

    fun bind(item: Card) {
        binding.item = item
    }
}