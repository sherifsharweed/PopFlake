package com.shekoo.popflake.model.entities

import com.google.gson.annotations.SerializedName

data class Trailer(

    @SerializedName("imDbId") val imDbId : String?,
    @SerializedName("title") val title : String?,
    @SerializedName("thumbnailUrl") val thumbnailUrl : String,
    @SerializedName("link") val link : String,
    @SerializedName("errorMessage") val errorMessage : String
)

