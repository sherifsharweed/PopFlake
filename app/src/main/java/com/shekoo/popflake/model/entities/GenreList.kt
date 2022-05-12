package com.shekoo.popflake.model.entities

import com.google.gson.annotations.SerializedName


data class GenreList (

	@SerializedName("key") val key : String,
	@SerializedName("value") val value : String
)