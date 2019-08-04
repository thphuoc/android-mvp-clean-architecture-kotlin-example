package com.android.data.remote.models.responses.error


import com.google.gson.annotations.SerializedName

data class Error(
    @SerializedName("msg")
    val msg: String = ""
)