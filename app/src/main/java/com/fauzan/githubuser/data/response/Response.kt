package com.fauzan.githubuser.data.response

import com.google.gson.annotations.SerializedName

data class SearchResponse(

	@field:SerializedName("total_count")
	val totalCount: Int,

	@field:SerializedName("incomplete_results")
	val incompleteResults: Boolean,

	@field:SerializedName("items")
	val items: List<User>
)

data class User(

	@field:SerializedName("login")
	val login: String,

	@field:SerializedName("avatar_url")
	val avatarUrl: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("name")
	val name: String?,

	@field:SerializedName("public_repos")
	val publicRepos: Int?,

	@field:SerializedName("public_gists")
	val publicGists: Int?,

	@field:SerializedName("followers")
	val followers: Int?,

	@field:SerializedName("following")
	val following: Int?,
)
