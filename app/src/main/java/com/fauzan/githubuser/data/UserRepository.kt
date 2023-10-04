package com.fauzan.githubuser.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.fauzan.githubuser.data.local.entity.UserEntity
import com.fauzan.githubuser.data.local.room.UserDao
import com.fauzan.githubuser.data.remote.retrofit.ApiService

class UserRepository private constructor(
    private val apiService: ApiService,
    private val userDao: UserDao,
) {
    private val _users = MediatorLiveData<Result<List<UserEntity>>>()
    val users: LiveData<Result<List<UserEntity>>> = _users

    suspend fun searchUsers(query: String) {
        _users.value = Result.Loading
        try {
            val response = apiService.getUsers(query)
            val users = response.users
            val usersList = users.map { user ->
                val isFavorite = userDao.checkUserIsFavorite(user.login)
                UserEntity(
                    user.login,
                    user.avatarUrl,
                    isFavorite = isFavorite
                )
            }
            userDao.deleteAll()
            userDao.insert(usersList)
        } catch (e: Exception) {
            _users.value = Result.Error(e.message.toString())
        }

        val localData: LiveData<Result<List<UserEntity>>> = userDao.getAll().map { Result.Success(it) }
        _users.addSource(localData) {
            _users.value = it
            _users.removeSource(localData)
        }
    }

    fun findUser(username: String): LiveData<Result<UserEntity>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getUserDetail(username)
            val user = UserEntity(
                response.login,
                response.avatarUrl,
                response.name,
                response.publicRepos ?: 0,
                response.publicGists ?: 0,
                response.followers ?: 0,
                response.following ?: 0
            )
            user.isFavorite = userDao.checkUserIsFavorite(username)
            emit(Result.Success(user))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getUserFollowers(username: String): LiveData<Result<List<UserEntity>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getUserFollowers(username)
            val users = response.map { user ->
                val isFavorite = userDao.checkUserIsFavorite(user.login)
                UserEntity(
                    user.login,
                    user.avatarUrl,
                    isFavorite = isFavorite
                )
            }
            emit(Result.Success(users))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getUserFollowing(username: String): LiveData<Result<List<UserEntity>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getUserFollowing(username)
            val users = response.map { user ->
                val isFavorite = userDao.checkUserIsFavorite(user.login)
                UserEntity(
                    user.login,
                    user.avatarUrl,
                    isFavorite = isFavorite
                )
            }
            emit(Result.Success(users))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: UserRepository? = null

        fun getInstance(apiService: ApiService, userDao: UserDao): UserRepository =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: UserRepository(apiService, userDao)
            }.also { INSTANCE = it }
    }
}