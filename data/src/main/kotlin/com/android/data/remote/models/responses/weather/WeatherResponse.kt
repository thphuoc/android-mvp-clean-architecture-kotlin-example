package com.android.data.remote.models.responses.weather


import com.google.gson.annotations.SerializedName

class WeatherResponse(
    @SerializedName("data")
    val `data`: Data = Data()
)