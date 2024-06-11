package com.example.plant.ui.network.response

import com.google.gson.annotations.SerializedName

data class HistoriesResponse(

	@field:SerializedName("data")
	val data: List<DataItem?>? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class DataItem(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("diseasesName")
	val diseasesName: String? = null,

	@field:SerializedName("imageUrl")
	val imageUrl: String? = null,

	@field:SerializedName("percentage")
	val percentage: Any? = null,

	@field:SerializedName("id")
	val id: String? = null
)
