package com.android.data.remote.models.responses.weather


import com.google.gson.annotations.SerializedName

data class Weather(
    @SerializedName("astronomy")
    val astronomy: List<Astronomy> = listOf(),
    @SerializedName("avgtempC")
    val avgtempC: String = "",
    @SerializedName("avgtempF")
    val avgtempF: String = "",
    @SerializedName("date")
    val date: String = "",
    @SerializedName("hourly")
    val hourly: List<Hourly> = listOf(),
    @SerializedName("maxtempC")
    val maxtempC: String = "",
    @SerializedName("maxtempF")
    val maxtempF: String = "",
    @SerializedName("mintempC")
    val mintempC: String = "",
    @SerializedName("mintempF")
    val mintempF: String = "",
    @SerializedName("sunHour")
    val sunHour: String = "",
    @SerializedName("totalSnow_cm")
    val totalSnowCm: String = "",
    @SerializedName("uvIndex")
    val uvIndex: String = ""
)