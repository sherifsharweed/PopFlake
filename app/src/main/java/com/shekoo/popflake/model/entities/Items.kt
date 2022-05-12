package com.shekoo.popflake.model.entities

import com.google.gson.annotations.SerializedName


data class Items (

	@SerializedName("id") val id : String,
	@SerializedName("rank") val rank : Int,
	@SerializedName("title") val title : String,
	@SerializedName("fullTitle") val fullTitle : String,
	@SerializedName("year") val year : Int,
	@SerializedName("image") val image : String,
	@SerializedName("crew") val crew : String,
	@SerializedName("imDbRating") val imDbRating : Double,
	@SerializedName("imDbRatingCount") val imDbRatingCount : Int
)