package com.example.plant.ui.network.response

import com.google.gson.annotations.SerializedName

data class GuidanceResponse(

	@field:SerializedName("data")
	val data: List<DataGuide?>? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class DataGuide(

	@field:SerializedName("publishedAt")
	val publishedAt: String? = null,

	@field:SerializedName("imageUrl")
	val imageUrl: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("title")
	val title: String? = null
)

data class PublishedAt(

	@field:SerializedName("_nanoseconds")
	val nanoseconds: Int? = null,

	@field:SerializedName("_seconds")
	val seconds: Int? = null
)
