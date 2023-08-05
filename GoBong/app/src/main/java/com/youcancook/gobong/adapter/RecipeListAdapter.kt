package com.youcancook.gobong.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.youcancook.gobong.databinding.ItemMainCardBinding
import com.youcancook.gobong.databinding.ItemRecipeAddBinding
import com.youcancook.gobong.databinding.ItemRecipeBinding
import com.youcancook.gobong.model.Recipe
import com.youcancook.gobong.model.RecipeAdd
import com.youcancook.gobong.model.RecipeStep

class RecipeListAdapter :
    ListAdapter<RecipeStep, RecyclerView.ViewHolder>(diffUtil) {

    inner class RecipeViewHolder(val binding: ItemRecipeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: RecipeStep) {
        }
    }

    inner class RecipeAddViewHolder(val binding: ItemRecipeAddBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: RecipeStep) {
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            0 -> RecipeViewHolder(
                ItemRecipeBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            1 ->
                RecipeAddViewHolder(
                    ItemRecipeAddBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )

            else -> RecipeViewHolder(
                ItemRecipeBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is RecipeViewHolder) {
            holder.bind(currentList[position])
        } else if (holder is RecipeAddViewHolder) {
            holder.bind(currentList[position])
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == currentList.size - 1) {
            1
        } else {
            0
        }
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<RecipeStep>() {
            override fun areItemsTheSame(oldItem: RecipeStep, newItem: RecipeStep): Boolean {
                return oldItem.title == newItem.title
            }

            override fun areContentsTheSame(oldItem: RecipeStep, newItem: RecipeStep): Boolean {
                return oldItem == newItem
            }

        }
    }
}