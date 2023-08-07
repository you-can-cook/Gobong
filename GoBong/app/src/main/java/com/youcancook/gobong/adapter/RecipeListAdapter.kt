package com.youcancook.gobong.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.youcancook.gobong.databinding.ItemMainCardBinding
import com.youcancook.gobong.databinding.ItemRecipeAddBinding
import com.youcancook.gobong.databinding.ItemRecipeBinding
import com.youcancook.gobong.model.Recipe
import com.youcancook.gobong.model.RecipeAdd
import com.youcancook.gobong.model.RecipeStep

class RecipeListAdapter(val onItemClick: (Int) -> Unit) :
    ListAdapter<Recipe, RecyclerView.ViewHolder>(diffUtil) {

    private var lastActivePosition = 0
    private var activePosition = 0

    fun activeRecipeStep(position: Int) {
        if (position == activePosition) return

        activePosition = position

        notifyItemChanged(lastActivePosition)
        lastActivePosition = activePosition
        notifyItemChanged(activePosition)
    }

    inner class RecipeViewHolder(val binding: ItemRecipeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.run {
                root.setOnClickListener {
                    activeRecipeStep(adapterPosition)
                }
            }
        }

        fun bind(data: RecipeStep) {
            binding.run {
                item = data
                selected = adapterPosition == activePosition
                levelTextView.text = "${adapterPosition + 1}단계"
                dashDivider.isVisible = adapterPosition != currentList.size - 1
            }
        }
    }

    inner class RecipeAddViewHolder(val binding: ItemRecipeAddBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: RecipeAdd) {
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
            holder.bind(currentList[position] as RecipeStep)
        } else if (holder is RecipeAddViewHolder) {
            holder.bind(currentList[position] as RecipeAdd)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (currentList[position] is RecipeAdd) {
            1
        } else {
            0
        }
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Recipe>() {
            override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
                return oldItem == newItem
            }

        }
    }
}