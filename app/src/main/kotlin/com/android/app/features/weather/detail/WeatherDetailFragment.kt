package com.android.app.features.weather.detail

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.app.R
import com.android.app.base.BaseFragment
import com.android.app.databinding.FragmentWeatherDetailBinding
import com.android.app.features.weather.detail.adapter.WeatherListAdapter
import com.android.domain.exceptions.EstablishConnectionException
import kotlinx.android.synthetic.main.fragment_weather_detail.*
import kotlinx.android.synthetic.main.view_weather_detail_state_loading.*
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class WeatherDetailFragment : BaseFragment() {
    override val layoutResId: Int = R.layout.fragment_weather_detail
    private val presenter: WeatherDetailPresenter by viewModel { parametersOf(this) }

    private lateinit var weatherAdapter: WeatherListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
        showLoading()

        presenter.loadWeathers()
        swipeRefreshWeatherView.setOnRefreshListener {
            swipeRefreshWeatherView.isRefreshing = true
            presenter.loadWeathers()
        }

        presenter.getWeathersLiveData().observe(this, Observer {
            weatherAdapter.dispatchUpdates(it)
            hideLoading()
        })

        btnBack.setOnClickListener { goBack() }
    }

    override fun bindingView(view: View) {
        FragmentWeatherDetailBinding.bind(view).apply {
            this.viewModel = this@WeatherDetailFragment.presenter
            lifecycleOwner = this@WeatherDetailFragment
        }
    }

    private fun setupUI() {
        rvWeathers.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        weatherAdapter = WeatherListAdapter(context, rvWeathers)
        rvWeathers.adapter = weatherAdapter
    }

    override fun showLoading() {
        shimmerLoadingView.visibility = View.VISIBLE
        shimmerLoadingView.startShimmer()
    }

    override fun hideLoading() {
        shimmerLoadingView.visibility = View.GONE
        shimmerLoadingView.stopShimmer()
        swipeRefreshWeatherView.isRefreshing = false
    }

    override fun onErrorHandle(throwable: Throwable) {
        if (throwable is EstablishConnectionException) {
            showErrorDialog(getString(R.string.error_no_connection)) {
                goBack()
            }
            return
        }
        super.onErrorHandle(throwable)
    }
}