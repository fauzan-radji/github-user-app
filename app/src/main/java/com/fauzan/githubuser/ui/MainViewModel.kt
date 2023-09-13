package com.fauzan.githubuser.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fauzan.githubuser.utils.Error
import com.fauzan.githubuser.data.response.SearchResponse
import com.fauzan.githubuser.data.response.User
import com.fauzan.githubuser.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel: ViewModel() {

    private var _users = MutableLiveData<List<User>?>()
    var users: LiveData<List<User>?> = _users

    private var _error = MutableLiveData<String>()
    var error: LiveData<String> = _error

    private val apiService = ApiConfig.getApiService()

    fun searchUsers(query: String) {
        val client = apiService.getUsers(query)
        client.enqueue(object: Callback<SearchResponse> {
            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>
            ) {
                if(response.isSuccessful) {
                    val responseBody = response.body()
                    if(responseBody == null) {
                        _users.value = null
                    } else {
                        _users.value = responseBody.items
                    }
                } else {
                    _error.value = when(response.code()) {
                        401 -> "401: Bad Request"
                        403 -> "403: Forbidden"
                        404 -> "404: Not Found"
                        else -> "Error: ${response.message()}"
                    }
                }
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                _error.value = "Error: ${t.message}"
            }
        })
    }
}