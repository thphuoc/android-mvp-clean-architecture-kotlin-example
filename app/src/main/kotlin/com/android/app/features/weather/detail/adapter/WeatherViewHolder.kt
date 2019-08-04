package com.android.app.features.weather.detail.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.app.R
import com.android.domain.entities.WeatherEntity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

typealias FieldUpdate = Int

class WeatherViewHolder(viewItem: View) :
        RecyclerView.ViewHolder(viewItem) {

    companion object {
        const val UPDATE_TIME: FieldUpdate = 0
        const val UPDATE_DESC: FieldUpdate = 1
        const val NO_UPDATE: FieldUpdate = -1
    }

    private val imgWeatherIcon: ImageView = viewItem.findViewById(R.id.imgWeather)
    private val tvObservationTime: TextView = viewItem.findViewById(R.id.tvObservationTimeValue)
    private val tvHumidity: TextView = viewItem.findViewById(R.id.tvHumidityValue)
    private val tvDesc: TextView = viewItem.findViewById(R.id.tvWeatherDescValue)

    fun bind(entity: WeatherEntity) {
        Glide.with(imgWeatherIcon).load(entity.icon).diskCacheStrategy(DiskCacheStrategy.ALL).into(imgWeatherIcon)
        tvObservationTime.text = entity.observationTime
        tvHumidity.text = entity.humidity
        tvDesc.text = entity.desc
    }

    fun bind(entity: WeatherEntity, fieldUpdated: FieldUpdate) {
        when (fieldUpdated) {
            UPDATE_TIME -> tvObservationTime.text = entity.observationTime
            UPDATE_DESC -> tvDesc.text = entity.desc
        }
    }
}