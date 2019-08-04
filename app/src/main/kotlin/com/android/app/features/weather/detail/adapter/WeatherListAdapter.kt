package com.android.app.features.weather.detail.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.android.app.R
import com.android.domain.entities.WeatherEntity

class WeatherListAdapter(
        private val context: Context?,
        private val recyclerView: RecyclerView,
        private val listWeatherEntity: ArrayList<WeatherEntity> = arrayListOf()
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    fun dispatchUpdates(newListWeatherEntity: List<WeatherEntity>) {
        val diffResult = DiffUtil.calculateDiff(
                WeatherEntityDiffCallback(
                        listWeatherEntity,
                        newListWeatherEntity
                )
        )
        listWeatherEntity.clear()
        listWeatherEntity.addAll(newListWeatherEntity)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return WeatherViewHolder(LayoutInflater.from(context).inflate(R.layout.item_view_weather, recyclerView, false))
    }

    override fun getItemCount() = listWeatherEntity.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as WeatherViewHolder).bind(listWeatherEntity[position])
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int, payloads: List<Any>) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position)
        } else {
            payloads.forEach { (holder as WeatherViewHolder).bind(listWeatherEntity[position], it as FieldUpdate) }
        }
    }
}