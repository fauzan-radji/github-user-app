package com.fauzan.githubuser.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fauzan.githubuser.data.response.User

class DetailViewModel: ApiViewModel() {

    private var _user = MutableLiveData<User>()
    var user: LiveData<User> = _user

    private var _users = MutableLiveData<List<User>>()
    var users: LiveData<List<User>> = _users

    fun getUserDetail(username: String) {
        request(
            client = apiService.getUserDetail(username),
            onResponse = { response ->
                if(response.isSuccessful) {
                    val responseBody = response.body()
                    if(responseBody != null) {
                        _user.value = responseBody
                    } else {
                        setError("Error: Response body is null")
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

    fun getUserFollowers(username: String) {
        request(
            client = apiService.getUserFollowers(username),
            onResponse = { response ->
                if(response.isSuccessful) {
                    val responseBody = response.body()
                    if(responseBody != null) {
                        _users.value = responseBody
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

    fun getUserFollowing(username: String) {
        request(
            client = apiService.getUserFollowing(username),
            onResponse = { response ->
                if(response.isSuccessful) {
                    val responseBody = response.body()
                    if(responseBody != null) {
                        _users.value = responseBody
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