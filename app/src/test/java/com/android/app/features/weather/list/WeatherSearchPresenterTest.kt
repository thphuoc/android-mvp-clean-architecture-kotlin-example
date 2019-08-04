package com.android.app.features.weather.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.android.app.features.weather.search.IWeatherSearchView
import com.android.app.features.weather.search.WeatherSearchPresenter
import com.android.data.local.dao.CitySuggestionRoomDAO
import com.android.data.local.entities.CitySuggestionRoomEntity
import com.android.data.remote.repositories.WeatherRepository
import com.android.domain.bus.AppBus
import com.android.domain.entities.CitySuggestionEntity
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.never
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations


@RunWith(JUnit4::class)
class WeatherSearchPresenterTest {

    @Mock
    lateinit var view: IWeatherSearchView
    @Mock
    lateinit var appBus: AppBus

    @Mock
    lateinit var citySuggestionRoomDAO: CitySuggestionRoomDAO

    @Mock
    lateinit var weatherRepository: WeatherRepository

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        Mockito.`when`(citySuggestionRoomDAO.getSuggestionCities()).thenReturn(
            Flowable.just(
                listOf(
                    CitySuggestionRoomEntity(0, "area", "region", "country")
                )
            )
        )
    }

    @Test
    fun should_cached_when_data_is_not_empty() {
        Mockito.`when`(weatherRepository.getSuggestionCities("abc")).thenReturn(
            Single.just(
                listOf(
                    CitySuggestionEntity("area", "region", "country")
                )
            )
        )
        Mockito.`when`(citySuggestionRoomDAO.clearAll()).thenReturn(Completable.complete())
        Mockito.`when`(citySuggestionRoomDAO.insert(any())).thenReturn(Completable.complete())
        val searchPresenter = WeatherSearchPresenter(view, appBus, weatherRepository, citySuggestionRoomDAO)
        searchPresenter.searchSuggestionsByText("abc")
        verify(citySuggestionRoomDAO, times(1)).clearAll()
        verify(citySuggestionRoomDAO, times(1)).insert(any())
    }

    @Test
    fun should_not_cached_when_data_is_empty() {
        Mockito.`when`(weatherRepository.getSuggestionCities("abc")).thenReturn(
            Single.just(emptyList())
        )
        val searchPresenter = WeatherSearchPresenter(view, appBus, weatherRepository, citySuggestionRoomDAO)
        searchPresenter.searchSuggestionsByText("abc")
        verify(citySuggestionRoomDAO, never()).clearAll()
        verify(citySuggestionRoomDAO, never()).insert(any())
    }

    @Test
    fun should_filter_list_not_empty_from_room_data() {
        val response = listOf(
            CitySuggestionRoomEntity(0, "area", "region", "country")
        )

        Mockito.`when`(citySuggestionRoomDAO.getSuggestionCities()).thenReturn(
            Flowable.just(response)
        )
        val searchPresenter = WeatherSearchPresenter(view, appBus, weatherRepository, citySuggestionRoomDAO)
        val testSubscriber = searchPresenter.getSuggestionCities().test()
        testSubscriber.assertValueCount(1)
        testSubscriber.assertValues(listOf(CitySuggestionEntity("area", "region", "country")))
    }

    @Test
    fun should_filter_list_empty_from_room_data() {
        val response = listOf<CitySuggestionRoomEntity>()

        Mockito.`when`(citySuggestionRoomDAO.getSuggestionCities()).thenReturn(
            Flowable.just(response)
        )
        val searchPresenter = WeatherSearchPresenter(view, appBus, weatherRepository, citySuggestionRoomDAO)
        val testSubscriber = searchPresenter.getSuggestionCities().test()
        testSubscriber.assertNoValues()
    }
}