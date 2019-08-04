package com.android.domain.entities

data class WeatherEntity(val icon: String,
                         var observationTime: String,
                         val humidity: String,
                         val desc: String)