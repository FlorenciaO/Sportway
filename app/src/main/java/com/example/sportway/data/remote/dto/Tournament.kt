package com.example.sportway.data.remote.dto

import com.google.gson.annotations.SerializedName

data class Tournament(
    @SerializedName("__belong")
    val belong: Any,
    @SerializedName("__children")
    val children: Any,
    val __v: Int,
    @SerializedName("_createDate")
    val createDate: String,
    @SerializedName("_externalId")
    val externalId: String,
    @SerializedName("_externalProvider")
    val _id: String,
    @SerializedName("_updateDate")
    val updateDate: String,
    val name: Name,
    val shortName: Any
)