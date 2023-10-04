package com.fauzan.githubuser.data.model

import com.fauzan.githubuser.data.local.entity.UserEntity

data class User(
    val login: String,
    val avatarUrl: String,
    val name: String? = null,
    val publicRepos: Int? = 0,
    val publicGists: Int? = 0,
    val followers: Int? = 0,
    val following: Int? = 0,
    var isFavorite: Boolean = false
) {
    fun toEntity() = UserEntity(
        login,
        avatarUrl,
        name,
        publicRepos ?: 0,
        publicGists ?: 0,
        followers ?: 0,
        following ?: 0
    )
}
