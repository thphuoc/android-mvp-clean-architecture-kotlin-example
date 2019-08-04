package com.android.app.features.weather.search.viewresolver

import android.widget.TextView
import com.android.app.R
import com.android.domain.entities.CitySuggestionEntity
import com.mindorks.placeholderview.annotations.Click
import com.mindorks.placeholderview.annotations.Layout
import com.mindorks.placeholderview.annotations.Resolve
import com.mindorks.placeholderview.annotations.View

@Layout(R.layout.item_view_city_suggestion)
class SuggestionCityViewResolver(private val city: CitySuggestionEntity,
                                 private val onItemClick: (city: CitySuggestionEntity) -> Unit) {

    @View(R.id.tvCityName)
    lateinit var tvCityName: TextView

    @Resolve
    fun onResolve() {
        tvCityName.text = "${city.region} (${city.area} - ${city.country})"
    }

    @Click(R.id.rlItemCity)
    fun onClickSuggestionItem() {
        onItemClick(city)
    }
}