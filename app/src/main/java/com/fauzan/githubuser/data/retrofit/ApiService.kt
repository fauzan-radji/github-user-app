package com.fauzan.githubuser.data.retrofit

import com.fauzan.githubuser.data.response.SearchResponse
import com.fauzan.githubuser.data.response.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    fun getUsers(@Query("q") username: String): Call<SearchResponse>

    @GET("users/{username}")
    fun getUserDetail(@Path("username") username: String): Call<User>
}