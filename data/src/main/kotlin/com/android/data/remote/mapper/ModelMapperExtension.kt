package com.android.data.remote.mapper

import com.android.data.remote.models.responses.weather.CurrentCondition
import com.android.data.remote.models.responses.weather.Result
import com.android.domain.entities.CitySuggestionEntity
import com.android.domain.entities.WeatherEntity

fun Result.toEntity() = CitySuggestionEntity(
        area = this.areaName.run { if (isEmpty()) "" else this[0].value },
        region = this.region.run { if (isEmpty()) "" else this[0].value },
        country = this.country.run { if (isEmpty()) "" else this[0].value }
)

fun CurrentCondition.toEntity() = WeatherEntity(
        icon = this.weatherIconUrl.run { if (isEmpty()) "" else this[0].value },
        observationTime = this.observationTime,
        humidity = this.humidity,
        desc = this.weatherDesc.run { if (isEmpty()) "" else this[0].value }
)