package com.android.data.remote.models.responses.weather


import com.google.gson.annotations.SerializedName

data class Astronomy(
    @SerializedName("moon_illumination")
    val moonIllumination: String = "",
    @SerializedName("moon_phase")
    val moonPhase: String = "",
    @SerializedName("moonrise")
    val moonrise: String = "",
    @SerializedName("moonset")
    val moonset: String = "",
    @SerializedName("sunrise")
    val sunrise: String = "",
    @SerializedName("sunset")
    val sunset: String = ""
)