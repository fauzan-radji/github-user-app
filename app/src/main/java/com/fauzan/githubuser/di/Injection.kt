package com.fauzan.githubuser.di

import android.content.Context
import com.fauzan.githubuser.data.UserRepository
import com.fauzan.githubuser.data.local.room.GithubUserDatabase
import com.fauzan.githubuser.data.remote.retrofit.ApiConfig

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val apiService = ApiConfig.getApiService()
        val database = GithubUserDatabase.getInstance(context)
        val dao = database.userDao()
        return UserRepository.getInstance(apiService, dao)
    }
}