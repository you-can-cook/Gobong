package com.youcancook.gobong.adapter

import android.content.Intent
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.youcancook.gobong.databinding.ItemMainCardBinding
import com.youcancook.gobong.model.Card
import com.youcancook.gobong.model.UserProfile
import com.youcancook.gobong.ui.my.other.OthersActivity

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
            profileImageView.setOnClickListener {
                val intent = Intent(binding.root.context, OthersActivity::class.java)
                binding.root.context.startActivity(intent)
            }
        }
    }

    fun bind(item: Card) {
        binding.item = item
        binding.followingButton.text = if(item.user.followed){
            "팔로잉"
        }
        else{
            "팔로우"
        }
    }
}