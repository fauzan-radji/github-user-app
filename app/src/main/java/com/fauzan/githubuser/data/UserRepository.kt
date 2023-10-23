package com.fauzan.githubuser.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.fauzan.githubuser.data.local.room.UserDao
import com.fauzan.githubuser.data.model.User
import com.fauzan.githubuser.data.remote.retrofit.ApiService

class UserRepository private constructor(
    private val apiService: ApiService,
    private val userDao: UserDao,
) {
    private val _users = MediatorLiveData<Result<List<User>>>()
    val users: LiveData<Result<List<User>>> = _users

    suspend fun searchUsers(query: String) {
        _users.value = Result.Loading
        try {
            val response = apiService.getUsers(query)
            val users = response.users
            val usersList = users.map { user ->
                User(
                    user.login,
                    user.avatarUrl
                )
            }
            _users.value = Result.Success(usersList)
        } catch (e: Exception) {
            _users.value = Result.Error(e.message.toString())
        }
    }

    fun findUser(username: String): LiveData<Result<User>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getUserDetail(username)
            val user = User(
                response.login,
                response.avatarUrl,
                response.name,
                response.publicRepos,
                response.publicGists,
                response.followers,
                response.following,
                false
            )

            if (userDao.isExists(username)) {
                user.isFavorite = true
            }

            emit(Result.Success(user))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getUserFollowers(username: String): LiveData<Result<List<User>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getUserFollowers(username)
            val users = response.map { user ->
                User(
                    user.login,
                    user.avatarUrl,
                    isFavorite = userDao.isExists(user.login)
                )
            }
            emit(Result.Success(users))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getUserFollowing(username: String): LiveData<Result<List<User>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getUserFollowing(username)
            val users = response.map { user ->
                User(
                    user.login,
                    user.avatarUrl,
                    isFavorite = userDao.isExists(user.login)
                )
            }
            emit(Result.Success(users))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getFavorites(): LiveData<Result<List<User>>> {
        val users = userDao.getAll()

        return users.map { userEntities ->
            Result.Success(userEntities.map { user ->
                User(
                    user.login,
                    user.avatarUrl,
                    user.name,
                    user.publicRepos,
                    user.publicGists,
                    user.followers,
                    user.following,
                    true
                )
            })
        }
    }

    suspend fun setFavorite(user: User, favoriteState: Boolean) {
        if (favoriteState) {
            userDao.insert(user.toEntity())
        } else {
            userDao.delete(user.toEntity())
        }

        user.isFavorite = favoriteState
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