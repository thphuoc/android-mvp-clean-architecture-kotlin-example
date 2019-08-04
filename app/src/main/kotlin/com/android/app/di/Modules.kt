package com.android.app.di

import android.content.Context
import com.android.app.BuildConfig
import com.android.app.base.IView
import com.android.app.features.weather.detail.WeatherDetailPresenter
import com.android.app.features.weather.search.IWeatherSearchView
import com.android.app.features.weather.search.WeatherSearchPresenter
import com.android.app.utils.DialogHelper
import com.android.data.local.AppDatabase
import com.android.data.remote.ServiceFactory
import com.android.data.remote.repositories.WeatherRepository
import com.android.data.remote.services.WeatherService
import com.android.domain.bus.AppBus
import com.android.domain.repositories.remote.IWeatherRepository
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    factory { (context: Context) -> DialogHelper(context) }
}

val useCaseModule = module {
    single { AppBus() }
}

val repositoryModule = module {
    single {
        hashMapOf(
            "key" to BuildConfig.WEATHER_API_KEY,
            "num_of_days" to BuildConfig.WEATHER_NUM_OF_DAYS,
            "fx" to BuildConfig.WEATHER_FX,
            "format" to BuildConfig.WEATHER_FORMAT
        )
    }

    single {
        ServiceFactory.createService(
            defaultQuery = get(),
            baseUrl = BuildConfig.BASE_URL,
            serviceClass = WeatherService::class.java,
            loggable = !BuildConfig.IS_PROD_FLAVOR
        )
    }

    single<IWeatherRepository> {
        WeatherRepository(get())
    }

    single { AppDatabase.getInstance(androidApplication()).getCitySuggestionDAO() }
}

val viewModelModule = module {
    viewModel { (view: IWeatherSearchView) -> WeatherSearchPresenter(view, get(), get(), get()) }
    viewModel { (view: IView) -> WeatherDetailPresenter(view, get(), get()) }
}