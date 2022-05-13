package com.shekoo.popflake.model.entities

import com.google.gson.annotations.SerializedName

data class Search(
    @SerializedName("searchType") val searchType : String?="",
    @SerializedName("expression") val expression : String?="",
    @SerializedName("results") val results : List<Results>?= emptyList(),
    @SerializedName("errorMessage") val errorMessage : String?=""
)
