package com.android.data.remote.models.responses.weather


import com.google.gson.annotations.SerializedName

data class SearchApi(
    @SerializedName("result")
    val result: List<Result> = listOf()
)