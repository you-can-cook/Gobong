package com.youcancook.gobong.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.youcancook.gobong.databinding.ItemMainCardBinding
import com.youcancook.gobong.databinding.ItemRecipeBinding
import com.youcancook.gobong.model.Recipe
import com.youcancook.gobong.model.RecipeStep

class RecipeListAdapter :
    ListAdapter<RecipeStep, RecipeListAdapter.ViewHolder>(diffUtil) {

    inner class ViewHolder(val binding: ItemRecipeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: RecipeStep) {
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemRecipeBinding.inflate(
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