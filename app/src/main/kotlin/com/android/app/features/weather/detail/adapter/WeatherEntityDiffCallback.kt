package com.android.app.features.weather.detail.adapter

import androidx.recyclerview.widget.DiffUtil
import com.android.domain.entities.WeatherEntity

class WeatherEntityDiffCallback(
        private val oldList: List<WeatherEntity>,
        private val newList: List<WeatherEntity>
) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldList[oldItemPosition] == newList[newItemPosition]

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldElement = oldList[oldItemPosition]
        val newElement = newList[newItemPosition]
        return oldElement.icon == newElement.icon &&
                oldElement.observationTime == newElement.observationTime &&
                oldElement.desc == newElement.desc &&
                oldElement.humidity == newElement.humidity
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        if (oldItem.observationTime != newItem.observationTime) return WeatherViewHolder.UPDATE_TIME
        if (oldItem.desc != newItem.desc) return WeatherViewHolder.UPDATE_DESC
        return WeatherViewHolder.NO_UPDATE
    }
}