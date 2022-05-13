package com.shekoo.popflake.model.entities

import com.google.gson.annotations.SerializedName

data class Results(

    @SerializedName("id") val id : String?,
    @SerializedName("resultType") val resultType : String?,
    @SerializedName("image") val image : String?,
    @SerializedName("title") val title : String?,
    @SerializedName("description") val description : String?
)
