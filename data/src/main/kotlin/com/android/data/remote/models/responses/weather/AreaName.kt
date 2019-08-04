package com.android.data.remote.models.responses.weather


import com.google.gson.annotations.SerializedName

data class AreaName(
    @SerializedName("value")
    val value: String = ""
)