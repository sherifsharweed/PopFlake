package com.shekoo.popflake.model.entities

import com.google.gson.annotations.SerializedName

data class DirectorList (

	@SerializedName("id") val id : String,
	@SerializedName("name") val name : String
)