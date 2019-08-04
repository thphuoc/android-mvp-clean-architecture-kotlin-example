package com.android.data.remote.repositories

import com.android.data.remote.models.responses.weather.GitOwnerInfo
import com.android.data.remote.models.responses.weather.GitRepoModel
import com.android.data.remote.services.WeatherService
import com.nhaarman.mockitokotlin2.any
import io.reactivex.Single
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class ListReposRepositoryTest {

    @Mock
    lateinit var service: WeatherService

    lateinit var repo: WeatherRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        repo = WeatherRepository(service)
    }

    @Test
    fun getListRepos_success_expect_mapping_model_correct() {
        val dummyData = arrayListOf(
            GitRepoModel(1, GitOwnerInfo("avatar"), "title", "desc"),
            GitRepoModel(2, GitOwnerInfo("avatar1"), "title1", "desc1")
        )
        Mockito.doReturn(Single.just(dummyData)).`when`(service).getListGitRepos(any(), any())
        val results = repo.getSuggestionCities(1, 25).blockingGet()

        Assert.assertEquals(2, results.size)

        Assert.assertEquals(1, results[0].id)
        Assert.assertEquals("avatar", results[0].avatar)
        Assert.assertEquals("title", results[0].title)
        Assert.assertEquals("desc", results[0].desc)
        Assert.assertEquals(2, results[1].id)
        Assert.assertEquals("avatar1", results[1].avatar)
        Assert.assertEquals("title1", results[1].title)
        Assert.assertEquals("desc1", results[1].desc)
    }
}