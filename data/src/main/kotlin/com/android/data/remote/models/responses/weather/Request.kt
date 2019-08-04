package com.android.data.remote.models.responses.weather


import com.google.gson.annotations.SerializedName

data class Request(
    @SerializedName("query")
    val query: String = "",
    @SerializedName("type")
    val type: String = ""
)