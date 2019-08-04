package com.android.data.remote.models.responses.weather


import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("areaName")
    val areaName: List<AreaName> = listOf(),
    @SerializedName("country")
    val country: List<Country> = listOf(),
    @SerializedName("latitude")
    val latitude: String = "",
    @SerializedName("longitude")
    val longitude: String = "",
    @SerializedName("population")
    val population: String = "",
    @SerializedName("region")
    val region: List<Region> = listOf(),
    @SerializedName("weatherUrl")
    val weatherUrl: List<WeatherUrl> = listOf()
)