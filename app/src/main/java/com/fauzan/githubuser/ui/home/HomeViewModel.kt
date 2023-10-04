package com.fauzan.githubuser.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fauzan.githubuser.data.Result
import com.fauzan.githubuser.data.UserRepository
import com.fauzan.githubuser.data.model.User
import kotlinx.coroutines.launch

class HomeViewModel(private val userRepository: UserRepository): ViewModel() {

    val users: LiveData<Result<List<User>>> = userRepository.users

    private val _searchQuery = MutableLiveData<String>()
    val searchQuery: LiveData<String> = _searchQuery

    fun searchUsers(query: String) {
        _searchQuery.value = query

        viewModelScope.launch {
            userRepository.searchUsers(query)
        }
    }
}