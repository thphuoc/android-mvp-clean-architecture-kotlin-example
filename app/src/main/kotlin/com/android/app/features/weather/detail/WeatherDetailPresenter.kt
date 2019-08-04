package com.android.app.features.weather.detail

import androidx.lifecycle.MutableLiveData
import com.android.app.base.BaseViewModel
import com.android.app.base.IView
import com.android.app.exts.observe
import com.android.domain.bus.AppBus
import com.android.domain.entities.WeatherEntity
import com.android.domain.repositories.remote.IWeatherRepository

class WeatherDetailPresenter(
        private val view: IView,
        private val appBus: AppBus,
        private val repo: IWeatherRepository) : BaseViewModel() {

    private val weathersLiveData = MutableLiveData<List<WeatherEntity>>()
    val cityName = MutableLiveData<String>().apply { value = appBus.citySelected?.area }

    fun loadWeathers() {
        appBus.citySelected?.let {
            repo.getWeathersByCity(it.area).observe(view) { weathers ->
                weathersLiveData.value = weathers
            }
        }
    }

    fun getWeathersLiveData() = weathersLiveData
}