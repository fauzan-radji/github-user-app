package com.fauzan.githubuser.data.retrofit

import com.fauzan.githubuser.data.response.SearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    fun getUsers(@Query("q") username: String): Call<SearchResponse>
}