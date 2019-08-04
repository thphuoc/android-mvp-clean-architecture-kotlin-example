package com.android.data.remote.models.responses.error


import com.google.gson.annotations.SerializedName

data class ErrorData(
    @SerializedName("error")
    val error: List<Error> = listOf()
)