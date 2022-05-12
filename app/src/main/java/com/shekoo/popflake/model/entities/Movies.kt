package com.shekoo.popflake.model.entities

import com.google.gson.annotations.SerializedName

data class Movies (

	@SerializedName("items") val items : List<Items>? = emptyList(),
	@SerializedName("errorMessage") val errorMessage : String? = ""
)