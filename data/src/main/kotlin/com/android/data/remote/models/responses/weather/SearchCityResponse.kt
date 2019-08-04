package com.android.data.remote.models.responses.weather


import com.android.data.remote.models.responses.BaseResponse
import com.google.gson.annotations.SerializedName

class SearchCityResponse(
        @SerializedName("search_api")
        val searchApi: SearchApi = SearchApi()
) : BaseResponse()