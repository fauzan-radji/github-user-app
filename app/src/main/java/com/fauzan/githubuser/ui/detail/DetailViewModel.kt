package com.fauzan.githubuser.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fauzan.githubuser.data.response.User
import com.fauzan.githubuser.ui.ApiViewModel
import retrofit2.Response

class DetailViewModel: ApiViewModel() {

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> = _users

    private val defaultOnFailure = { t: Throwable ->
        setError("Error: ${t.message}")
    }

    private val defaultGetUsers = { response: Response<List<User>?> ->
        if(response.isSuccessful && response.body() != null) {
            _users.postValue(response.body())
        } else {
            setError(when(response.code()) {
                401 -> "401: Bad Request"
                403 -> "403: Forbidden"
                404 -> "404: Not Found"
                else -> "Error: ${response.message()}"
            })
        }
    }

    fun getUserDetail(username: String) {
        request(
            client = apiService.getUserDetail(username),
            onResponse = { response ->
                if(response.isSuccessful && response.body() != null) {
                    _user.postValue(response.body())
                } else {
                    setError(when(response.code()) {
                        401 -> "401: Bad Request"
                        403 -> "403: Forbidden"
                        404 -> "404: Not Found"
                        else -> "Error: ${response.message()}"
                    })
                }
            },
            onFailure = defaultOnFailure
        )
    }

    fun getUserFollowers(username: String) {
        request(
            client = apiService.getUserFollowers(username),
            onResponse = defaultGetUsers,
            onFailure = defaultOnFailure
        )
    }

    fun getUserFollowing(username: String) {
        request(
            client = apiService.getUserFollowing(username),
            onResponse = defaultGetUsers,
            onFailure = defaultOnFailure
        )
    }
}