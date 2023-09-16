package com.fauzan.githubuser.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fauzan.githubuser.data.response.User

class HomeViewModel: ApiViewModel() {

    private var _users = MutableLiveData<List<User>?>()
    var users: LiveData<List<User>?> = _users

    fun searchUsers(query: String) {
        request(
            client = apiService.getUsers(query),
            onResponse = { response ->
                if(response.isSuccessful) {
                    val responseBody = response.body()
                    if(responseBody == null) {
                        _users.value = null
                    } else {
                        _users.value = responseBody.items
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
            onFailure = { t ->
                setError("Error: ${t.message}")
            }
        )
    }
}