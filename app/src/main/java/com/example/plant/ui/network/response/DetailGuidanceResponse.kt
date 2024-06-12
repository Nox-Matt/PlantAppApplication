package com.example.plant.ui.network.response

import com.google.gson.annotations.SerializedName

data class DetailGuidanceResponse(

	@field:SerializedName("data")
	val data: DataDetailGuide? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class ContentItem(

	@field:SerializedName("step3Body")
	val step3Body: String? = null,

	@field:SerializedName("step2Body")
	val step2Body: String? = null,

	@field:SerializedName("step2Title")
	val step2Title: String? = null,

	@field:SerializedName("step3Title")
	val step3Title: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("step1Body")
	val step1Body: String? = null,

	@field:SerializedName("step1Title")
	val step1Title: String? = null,

	@field:SerializedName("step4Body")
	val step4Body: String? = null,

	@field:SerializedName("opening")
	val opening: String? = null,

	@field:SerializedName("step4Title")
	val step4Title: String? = null,

	@field:SerializedName("step5Title")
	val step5Title: String? = null,

	@field:SerializedName("step5Body")
	val step5Body: String? = null
)

data class DataDetailGuide(

	@field:SerializedName("publishedAt")
	val publishedAt: PublishedAtDetailGuide? = null,

	@field:SerializedName("imageUrl")
	val imageUrl: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("content")
	val content: List<ContentItem?>? = null
)

data class PublishedAtDetailGuide(

	@field:SerializedName("_nanoseconds")
	val nanoseconds: Int? = null,

	@field:SerializedName("_seconds")
	val seconds: Int? = null
)
