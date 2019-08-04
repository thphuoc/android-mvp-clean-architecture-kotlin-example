package com.android.app.features.weather.search

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import com.android.app.R
import com.android.app.base.BaseFragment
import com.android.app.exts.observe
import com.android.app.features.weather.detail.WeatherDetailActivity
import com.android.app.features.weather.search.viewresolver.SuggestionCityViewResolver
import com.android.domain.entities.CitySuggestionEntity
import kotlinx.android.synthetic.main.fragment_weather_search.*
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class WeatherSearchFragment : BaseFragment(), IWeatherSearchView {
    override val layoutResId: Int = R.layout.fragment_weather_search
    private val searchPresenter: WeatherSearchPresenter by viewModel { parametersOf(this) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnSearch.setOnClickListener {
            searchByText(edtSearchCity.text.toString())
        }

        edtSearchCity.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                searchByText(edtSearchCity.text.toString())
                return@setOnKeyListener true
            }
            return@setOnKeyListener false
        }

        edtSearchCity.requestFocus()

        searchPresenter.getSuggestionCities().observe(this) {
            hideLoading()
            it?.apply {
                showListSuggestionsCity(this)
            }
        }
    }

    private fun showListSuggestionsCity(listCity: List<CitySuggestionEntity>) {
        rvSuggestionCities.removeAllViews()
        tvStatus.visibility = View.GONE
        if (listCity.isEmpty()) {
            showEmptyStatus()
        }

        for (citySuggestionEntity in listCity) {
            rvSuggestionCities.addView(SuggestionCityViewResolver(citySuggestionEntity) { cityEntity ->
                searchPresenter.onClickSuggestionItem(cityEntity)
            })
        }
    }

    private fun searchByText(text: String) {
        showLoading()
        tvStatus.visibility = View.GONE
        searchPresenter.searchSuggestionsByText(text)
    }

    private fun showEmptyStatus() {
        showErrorDialog(getString(R.string.lb_city_not_found))
    }

    override fun showWeatherDetail() {
        goNext(WeatherDetailActivity::class.java)
    }
}