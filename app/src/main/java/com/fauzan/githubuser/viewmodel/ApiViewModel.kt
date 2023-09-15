package com.fauzan.githubuser.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fauzan.githubuser.data.retrofit.ApiConfig

abstract class ApiViewModel: ViewModel() {
    private var _loading = MutableLiveData<Boolean>()
    var loading: LiveData<Boolean> = _loading
    protected fun setLoading(value: Boolean) {
        _loading.value = value
    }

    private var _error = MutableLiveData<String>()
    var error: LiveData<String> = _error
    protected fun setError(value: String) {
        _error.value = value
    }

    protected val apiService = ApiConfig.getApiService()
}