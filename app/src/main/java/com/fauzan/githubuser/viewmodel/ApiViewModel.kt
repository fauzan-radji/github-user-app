package com.fauzan.githubuser.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fauzan.githubuser.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

abstract class ApiViewModel: ViewModel() {
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading
    protected fun setLoading(value: Boolean) {
        _loading.postValue(value)
    }

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error
    protected fun setError(value: String) {
        _error.postValue(value)
    }

    protected val apiService = ApiConfig.getApiService()

    protected fun <T>request(client: Call<T>, onResponse: (response: Response<T>) -> Unit, onFailure: (t: Throwable) -> Unit) {
        setLoading(true)
        client.enqueue(object: Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                setLoading(false)
                onResponse(response)
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                setLoading(false)
                onFailure(t)
            }
        })
    }
}