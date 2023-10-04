package com.fauzan.githubuser.diffutil

import androidx.recyclerview.widget.DiffUtil
import com.fauzan.githubuser.data.local.entity.UserEntity

class UserDiffUtilCallback(private val oldUsers: List<UserEntity>, private val newUsers: List<UserEntity>) : DiffUtil.Callback() {
    override fun getOldListSize() = oldUsers.size
    override fun getNewListSize() = newUsers.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) = oldUsers[oldItemPosition].login == newUsers[newItemPosition].login

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return when {
            oldUsers[oldItemPosition].login != newUsers[newItemPosition].login -> false
            oldUsers[oldItemPosition].avatarUrl != newUsers[newItemPosition].avatarUrl -> false
            else -> true
        }
    }
}