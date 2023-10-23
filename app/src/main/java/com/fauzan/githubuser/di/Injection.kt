package com.fauzan.githubuser.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.fauzan.githubuser.data.UserRepository
import com.fauzan.githubuser.data.datastore.ThemePreferences
import com.fauzan.githubuser.data.local.room.GithubUserDatabase
import com.fauzan.githubuser.data.remote.retrofit.ApiConfig

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val apiService = ApiConfig.getApiService()
        val database = GithubUserDatabase.getInstance(context)
        val dao = database.userDao()
        return UserRepository.getInstance(apiService, dao)
    }

    fun providePreferences(dataStore: DataStore<Preferences>): ThemePreferences {
        return ThemePreferences.getInstance(dataStore)
    }
}