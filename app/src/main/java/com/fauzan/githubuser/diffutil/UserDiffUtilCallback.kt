package com.fauzan.githubuser.diffutil

import androidx.recyclerview.widget.DiffUtil
import com.fauzan.githubuser.data.model.User

class UserDiffUtilCallback(private val oldUsers: List<User>, private val newUsers: List<User>) : DiffUtil.Callback() {
    override fun getOldListSize() = oldUsers.size
    override fun getNewListSize() = newUsers.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) = oldUsers[oldItemPosition].login == newUsers[newItemPosition].login

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return when {
            oldUsers[oldItemPosition].login != newUsers[newItemPosition].login -> false
            oldUsers[oldItemPosition].avatarUrl != newUsers[newItemPosition].avatarUrl -> false
            oldUsers[oldItemPosition].name != newUsers[newItemPosition].name -> false
            oldUsers[oldItemPosition].publicRepos != newUsers[newItemPosition].publicRepos -> false
            oldUsers[oldItemPosition].publicGists != newUsers[newItemPosition].publicGists -> false
            oldUsers[oldItemPosition].followers != newUsers[newItemPosition].followers -> false
            oldUsers[oldItemPosition].following != newUsers[newItemPosition].following -> false
            oldUsers[oldItemPosition].isFavorite != newUsers[newItemPosition].isFavorite -> false
            else -> true
        }
    }
}