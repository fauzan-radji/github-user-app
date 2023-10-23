package com.fauzan.githubuser.ui.favorite

import androidx.lifecycle.ViewModel
import com.fauzan.githubuser.data.UserRepository

class FavoriteViewModel(private val userRepository: UserRepository) : ViewModel() {
        fun getFavoriteUsers()= userRepository.getFavorites()
}