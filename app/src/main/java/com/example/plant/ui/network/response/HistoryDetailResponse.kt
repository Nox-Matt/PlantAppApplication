package com.example.plant.ui.network.response

import com.google.gson.annotations.SerializedName

data class HistoryDetailResponse(

	@field:SerializedName("data")
	val data: DataDetail? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class DataDetail(

	@field:SerializedName("treatment")
	val treatment: String? = null,

	@field:SerializedName("diseasesName")
	val diseasesName: String? = null,

	@field:SerializedName("imageUrl")
	val imageUrl: String? = null,

	@field:SerializedName("percentage")
	val percentage: Any? = null,

	@field:SerializedName("causes")
	val causes: String? = null,

	@field:SerializedName("description")
	val description: String? = null
)
