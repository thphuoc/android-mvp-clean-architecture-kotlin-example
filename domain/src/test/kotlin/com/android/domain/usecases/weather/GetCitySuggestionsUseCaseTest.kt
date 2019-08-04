package com.android.domain.usecases.weather

import com.android.domain.bus.AppBus
import com.android.domain.entities.CitySuggestionEntity
import com.android.domain.repositories.remote.IWeatherRepository
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class GetCitySuggestionsUseCaseTest {

    @Mock
    lateinit var repo: IWeatherRepository

    @Mock
    lateinit var appBus: AppBus

    private lateinit var useCase: GetCitySuggestionsUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        useCase = GetCitySuggestionsUseCase(repo, appBus)
    }

    @Test
    fun getListRepos_success() {
        val dummyData: List<CitySuggestionEntity> = arrayListOf(
            CitySuggestionEntity(1, "avatar1", "title1", "desc1"),
            CitySuggestionEntity(2, "avatar2", "title2", "desc2")
        )

        Mockito.doReturn(Single.just(dummyData)).`when`(repo).getSuggestionCities(any(), any())
        val testObservable = useCase.getListRepos(1).test()
        verify(repo, times(1)).getSuggestionCities(any(), any())
        testObservable.assertValue(dummyData)
        testObservable.dispose()
    }
}