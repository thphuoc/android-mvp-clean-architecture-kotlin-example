package com.android.data.remote.models.responses.weather


import com.google.gson.annotations.SerializedName

data class ClimateAverage(
    @SerializedName("month")
    val month: List<Month> = listOf()
)