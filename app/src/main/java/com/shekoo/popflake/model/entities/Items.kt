package com.shekoo.popflake.model.entities

import com.google.gson.annotations.SerializedName


data class Items (

	@SerializedName("id") val id : String?,
	@SerializedName("title") val title : String?,
	@SerializedName("fullTitle") val fullTitle : String?,
	@SerializedName("year") val year : String?,
	@SerializedName("releaseState") val releaseState : String?,
	@SerializedName("image") val image : String?,
	@SerializedName("runtimeMins") val runtimeMins : String?,
	@SerializedName("runtimeStr") val runtimeStr : String?,
	@SerializedName("plot") val plot : String?,
	@SerializedName("contentRating") val contentRating : String?,
	@SerializedName("imDbRating") val imDbRating : String?,
	@SerializedName("imDbRatingCount") val imDbRatingCount : String?,
	@SerializedName("metacriticRating") val metacriticRating : String?,
	@SerializedName("genres") val genres : String?,



)