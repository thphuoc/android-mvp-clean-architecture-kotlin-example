package com.android.app.features.weather.search

import com.android.app.base.BaseViewModel
import com.android.app.exts.observe
import com.android.data.local.dao.CitySuggestionRoomDAO
import com.android.data.local.toEntity
import com.android.data.local.toRoomEntity
import com.android.domain.bus.AppBus
import com.android.domain.entities.CitySuggestionEntity
import com.android.domain.repositories.remote.IWeatherRepository
import com.orhanobut.logger.Logger

class WeatherSearchPresenter(
    private val view: IWeatherSearchView,
    private val appBus: AppBus,
    private val weatherRepo: IWeatherRepository,
    private val citySuggestionRoomDAO: CitySuggestionRoomDAO
) : BaseViewModel() {

    private val suggestionCities = citySuggestionRoomDAO.getSuggestionCities()
        .filter { it.isNotEmpty() }
        .map { it.map { roomEntity -> roomEntity.toEntity() } }

    fun searchSuggestionsByText(searchText: String) {
        weatherRepo.getSuggestionCities(searchText)
            .doOnSuccess { listSuggestions ->
                if (listSuggestions.isNotEmpty()) {
                    citySuggestionRoomDAO.clearAll().blockingGet()?.printStackTrace()
                    citySuggestionRoomDAO.insert((listSuggestions.map { city ->
                        city.toRoomEntity()
                    })).blockingGet()?.printStackTrace()
                }
            }
            .observe(view) {
                Logger.d("Completed request")
            }
    }

    fun getSuggestionCities() = suggestionCities

    fun onClickSuggestionItem(citySuggestionEntity: CitySuggestionEntity) {
        appBus.citySelected = citySuggestionEntity
        view.showWeatherDetail()
    }
}