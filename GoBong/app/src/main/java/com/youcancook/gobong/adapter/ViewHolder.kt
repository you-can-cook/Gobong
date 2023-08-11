package com.youcancook.gobong.adapter

import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.youcancook.gobong.databinding.ItemMainCardBinding
import com.youcancook.gobong.model.Card

class CardViewHolder(
    val binding: ItemMainCardBinding,
    adapter: ListAdapter<*, *>,
    onItemClick: (Card) -> Unit,
) :
    RecyclerView.ViewHolder(binding.root) {

    init {
        binding.root.setOnClickListener {
            onItemClick(adapter.currentList[adapterPosition] as Card)
        }
    }

    fun bind(item: Card) {
        println("bind!!! item")
        binding.item = item
    }
}