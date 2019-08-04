package com.android.data.remote.models.responses.error


import com.google.gson.annotations.SerializedName

open class ErrorResponse(
    @SerializedName("data")
    val errorData: ErrorData = ErrorData()
)