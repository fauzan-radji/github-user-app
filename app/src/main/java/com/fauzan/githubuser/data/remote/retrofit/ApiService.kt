package com.fauzan.githubuser.data.remote.retrofit

import com.fauzan.githubuser.data.remote.response.SearchResponse
import com.fauzan.githubuser.data.remote.response.UserResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    suspend fun getUsers(@Query("q") username: String): SearchResponse

    @GET("users/{username}")
    suspend fun getUserDetail(@Path("username") username: String): UserResponse

    @GET("users/{username}/followers")
    suspend fun getUserFollowers(@Path("username") username: String): List<UserResponse>

    @GET("users/{username}/following")
    suspend fun getUserFollowing(@Path("username") username: String): List<UserResponse>
}