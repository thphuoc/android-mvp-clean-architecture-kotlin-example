package com.android.data.remote.repositories

import com.android.data.remote.mapper.toEntity
import com.android.data.remote.services.WeatherService
import com.android.domain.entities.CitySuggestionEntity
import com.android.domain.entities.WeatherEntity
import com.android.domain.repositories.remote.IWeatherRepository
import io.reactivex.Single


class WeatherRepository(
        private val service: WeatherService
) : IWeatherRepository {

    override fun getSuggestionCities(searchText: String): Single<List<CitySuggestionEntity>> =
            service.searchCityByText(searchText).map { response ->
                response.searchApi.result.map { it.toEntity() }
            }

    override fun getWeathersByCity(cityName: String): Single<List<WeatherEntity>> =
            service.getWeathersByCity(cityName).map { response ->
                response.data.currentCondition.map { it.toEntity() }
            }
}