package com.fauzan.githubuser.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fauzan.githubuser.R
import com.fauzan.githubuser.data.model.User
import com.fauzan.githubuser.databinding.ItemUserBinding
import com.fauzan.githubuser.diffutil.UserDiffUtilCallback

class UserAdapter(private val users: MutableList<User>, private val onItemClickCallback: (User) -> Unit) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {
    fun updateData(newUsers: List<User>) {
        val diffCallback = UserDiffUtilCallback(users, newUsers)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        users.clear()
        users.addAll(newUsers)
        diffResult.dispatchUpdatesTo(this)
    }

    class ViewHolder(private val binding: ItemUserBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            binding.tvUsername.text = user.login
            binding.tvUserUrl.text = itemView.resources.getString(R.string.user_url, user.login)
            Glide.with(itemView)
                .load(user.avatarUrl)
                .placeholder(R.drawable.default_avatar)
                .into(binding.ivProfile)
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