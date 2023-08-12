package com.youcancook.gobong.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.youcancook.gobong.databinding.ItemFollowBinding
import com.youcancook.gobong.model.User

class FollowListAdapter : ListAdapter<User, FollowListAdapter.FollowViewHolder>(diffUtil) {

    inner class FollowViewHolder(val binding: ItemFollowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: User) {

        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): FollowListAdapter.FollowViewHolder {
        return FollowViewHolder(
            ItemFollowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: FollowListAdapter.FollowViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem.nickname == newItem.nickname
            }

            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem == newItem
            }

        }
    }

}