package com.android.data.remote.models.responses.weather


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("ClimateAverages")
    val climateAverages: List<ClimateAverage> = listOf(),
    @SerializedName("current_condition")
    val currentCondition: List<CurrentCondition> = listOf(),
    @SerializedName("request")
    val request: List<Request> = listOf(),
    @SerializedName("weather")
    val weather: List<Weather> = listOf()
)