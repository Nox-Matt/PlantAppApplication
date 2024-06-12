package com.example.plant.ui.network.response

import com.google.gson.annotations.SerializedName

data class AddForumResponse(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)
