package com.fauzan.githubuser.diffutil

import androidx.recyclerview.widget.DiffUtil
import com.fauzan.githubuser.data.response.User

class UserDiffUtilCallback(private val oldUsers: List<User>, private val newUsers: List<User>) : DiffUtil.Callback() {
    override fun getOldListSize() = oldUsers.size
    override fun getNewListSize() = newUsers.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) = oldUsers[oldItemPosition].id == newUsers[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return when {
            oldUsers[oldItemPosition].id != newUsers[newItemPosition].id -> false
            oldUsers[oldItemPosition].login != newUsers[newItemPosition].login -> false
            oldUsers[oldItemPosition].avatarUrl != newUsers[newItemPosition].avatarUrl -> false
            else -> true
        }
    }
}