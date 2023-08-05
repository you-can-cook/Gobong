package com.youcancook.gobong.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.youcancook.gobong.databinding.ItemGridCardBinding
import com.youcancook.gobong.model.Card

class GridRecyclerViewListAdapter :
    ListAdapter<Card, GridRecyclerViewListAdapter.ViewHolder>(diffUtil) {

    inner class ViewHolder(val binding: ItemGridCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Card) {
            binding.item = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemGridCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Card>() {
            override fun areItemsTheSame(oldItem: Card, newItem: Card): Boolean {
                return oldItem.title == newItem.title
            }

            override fun areContentsTheSame(oldItem: Card, newItem: Card): Boolean {
                return oldItem == newItem
            }

        }
    }
}