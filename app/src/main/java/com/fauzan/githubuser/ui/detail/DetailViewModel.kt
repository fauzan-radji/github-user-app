package com.fauzan.githubuser.ui.detail

import androidx.lifecycle.ViewModel
import com.fauzan.githubuser.data.UserRepository

class DetailViewModel(private val userRepository: UserRepository) : ViewModel() {
    fun getUserDetail(username: String) = userRepository.findUser(username)
    fun getUserFollowers(username: String) = userRepository.getUserFollowers(username)
    fun getUserFollowing(username: String) = userRepository.getUserFollowing(username)
}