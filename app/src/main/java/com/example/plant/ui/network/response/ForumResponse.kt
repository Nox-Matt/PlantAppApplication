package com.example.plant.ui.network.response

import com.google.gson.annotations.SerializedName

data class ForumResponse(

	@field:SerializedName("data")
	val data: List<DataForumItem?>? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class DataForumItem(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("question")
	val question: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("username")
	val username: String? = null
)
