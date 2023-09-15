package com.fauzan.githubuser.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fauzan.githubuser.data.response.SearchResponse
import com.fauzan.githubuser.data.response.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel: ApiViewModel() {

    private var _users = MutableLiveData<List<User>?>()
    var users: LiveData<List<User>?> = _users

    fun searchUsers(query: String) {
        setLoading(true)
        val client = apiService.getUsers(query)
        client.enqueue(object: Callback<SearchResponse> {
            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>
            ) {
                setLoading(false)
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
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                setLoading(false)
                setError("Error: ${t.message}")
            }
        })
    }
}