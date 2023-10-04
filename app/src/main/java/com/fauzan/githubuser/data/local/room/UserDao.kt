package com.fauzan.githubuser.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.fauzan.githubuser.data.local.entity.UserEntity

@Dao
interface UserDao {
    @Query("SELECT * FROM users")
    fun getAll(): LiveData<List<UserEntity>>

    @Query("SELECT * FROM users WHERE isFavorite = 1")
    fun getFavoriteUsers(): LiveData<List<UserEntity>>

    @Query("SELECT * FROM users WHERE login = :username")
    fun find(username: String): LiveData<UserEntity>

    @Query("SELECT * FROM users WHERE login LIKE :username")
    fun search(username: String): LiveData<List<UserEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(users: List<UserEntity>)

    @Query("DELETE FROM users WHERE isFavorite = 0")
    suspend fun deleteAll()

    @Query("SELECT EXISTS(SELECT * FROM users WHERE login = :username AND isFavorite = 1)")
    suspend fun checkUserIsFavorite(username: String): Boolean
}