package com.android.data.remote.services

import com.android.data.remote.models.responses.weather.SearchCityResponse
import com.android.data.remote.models.responses.weather.WeatherResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("/premium/v1/search.ashx")
    fun searchCityByText(@Query("q") searchText: String): Single<SearchCityResponse>

    @GET("/premium/v1/weather.ashx")
    fun getWeathersByCity(@Query("q") cityName: String): Single<WeatherResponse>
}