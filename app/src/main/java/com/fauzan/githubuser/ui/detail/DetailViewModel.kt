package com.fauzan.githubuser.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fauzan.githubuser.data.UserRepository
import com.fauzan.githubuser.data.model.User
import kotlinx.coroutines.launch

class DetailViewModel(private val userRepository: UserRepository) : ViewModel() {
    fun getUserDetail(username: String) = userRepository.findUser(username)
    fun getUserFollowers(username: String) = userRepository.getUserFollowers(username)
    fun getUserFollowing(username: String) = userRepository.getUserFollowing(username)

    fun addFavorite(user: User) {
        viewModelScope.launch {
            userRepository.setFavorite(user, true)
        }
    }

    fun removeFavorite(user: User) {
        viewModelScope.launch {
            userRepository.setFavorite(user, false)
        }
    }
}