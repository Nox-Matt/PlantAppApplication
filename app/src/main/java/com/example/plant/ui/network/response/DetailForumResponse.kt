package com.example.plant.ui.network.response

import com.google.gson.annotations.SerializedName

data class DetailForumResponse(

	@field:SerializedName("data")
	val data: AnswersItem? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class AnswersItem(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("answer")
	val answer: List<DataComment?>? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("username")
	val username: String? = null
)

data class DataComment(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("question")
	val question: String? = null,

	@field:SerializedName("answers")
	val answers: List<AnswersItem?>? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("username")
	val username: String? = null
)
