package com.android.domain.repositories.remote

import com.android.domain.entities.CitySuggestionEntity
import com.android.domain.entities.WeatherEntity
import io.reactivex.Single

interface IWeatherRepository {
    fun getSuggestionCities(searchText: String): Single<List<CitySuggestionEntity>>
    fun getWeathersByCity(cityName: String): Single<List<WeatherEntity>>
}