package com.android.data.remote.models.responses.weather


import com.google.gson.annotations.SerializedName

data class WeatherIconUrl(
    @SerializedName("value")
    val value: String = ""
)