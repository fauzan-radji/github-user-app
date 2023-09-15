package com.fauzan.githubuser.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fauzan.githubuser.data.response.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel: ApiViewModel() {

    private var _user = MutableLiveData<User>()
    var user: LiveData<User> = _user

    fun getUserDetail(username: String) {
        setLoading(true)
        val client = apiService.getUserDetail(username)
        client.enqueue(object: Callback<User> {
            override fun onResponse(
                call: Call<User>,
                response: Response<User>
            ) {
                setLoading(false)
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
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                setLoading(false)
                setError("Error: ${t.message}")
            }
        })
    }
}