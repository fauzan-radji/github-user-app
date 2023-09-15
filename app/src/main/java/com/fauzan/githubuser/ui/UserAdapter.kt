package com.fauzan.githubuser.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fauzan.githubuser.R
import com.fauzan.githubuser.data.response.User
import com.fauzan.githubuser.databinding.ItemUserBinding

class UserAdapter(private val users: List<User>, private val onItemClickCallback: (User) -> Unit) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {
    class ViewHolder(private val binding: ItemUserBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            binding.tvUsername.text = user.login
            binding.tvUserUrl.text = itemView.resources.getString(R.string.user_url, user.login)
            Glide.with(itemView).load(user.avatarUrl).into(binding.ivProfile)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = users.size
    
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = users[position]
        holder.bind(user)
        holder.itemView.setOnClickListener {
            onItemClickCallback(user)
        }
    }
}