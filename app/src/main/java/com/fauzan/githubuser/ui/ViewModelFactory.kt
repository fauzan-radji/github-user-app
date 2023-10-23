package com.fauzan.githubuser.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fauzan.githubuser.data.UserRepository
import com.fauzan.githubuser.data.datastore.ThemePreferences
import com.fauzan.githubuser.data.datastore.dataStore
import com.fauzan.githubuser.di.Injection
import com.fauzan.githubuser.ui.detail.DetailViewModel
import com.fauzan.githubuser.ui.favorite.FavoriteViewModel
import com.fauzan.githubuser.ui.home.HomeViewModel
import com.fauzan.githubuser.ui.settings.SettingsViewModel

class ViewModelFactory private constructor(private val userRepository: UserRepository, private val pref: ThemePreferences) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> HomeViewModel(userRepository) as T
            modelClass.isAssignableFrom(DetailViewModel::class.java) -> DetailViewModel(userRepository) as T
            modelClass.isAssignableFrom(FavoriteViewModel::class.java) -> FavoriteViewModel(userRepository) as T
            modelClass.isAssignableFrom(SettingsViewModel::class.java) -> SettingsViewModel(pref) as T

            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: ViewModelFactory(Injection.provideRepository(context), Injection.providePreferences(context.dataStore))
            }.also { INSTANCE = it }
    }
}