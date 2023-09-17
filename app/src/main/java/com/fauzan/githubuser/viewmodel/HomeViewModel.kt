package com.fauzan.githubuser.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fauzan.githubuser.data.response.User

class HomeViewModel: ApiViewModel() {

    private var _users = MutableLiveData<List<User>?>()
    var users: LiveData<List<User>?> = _users

    private var _searchQuery = MutableLiveData<String>()
    var searchQuery: LiveData<String> = _searchQuery

    fun searchUsers(query: String) {
        _searchQuery.value = query
        if(query.isEmpty()) {
            _users.value = null
        } else {
            request(
                client = apiService.getUsers(query),
                onResponse = { response ->
                    if(response.isSuccessful) {
                        val responseBody = response.body()
                        if(responseBody == null) {
                            _users.postValue(null)
                        } else {
                            _users.postValue(responseBody.items)
                        }
                    } else {
                        setError(when(response.code()) {
                            401 -> "401: Bad Request"
                            403 -> "403: Forbidden"
                            404 -> "404: Not Found"
                            else -> "Error: ${response.message()}"
                        })
                    }
                },
                onFailure = { error ->
                    setError("Error: ${error.message}")
                }
            )
        }
    }
}