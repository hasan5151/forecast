package com.beeline.forecast.repo

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.beeline.forecast.MainCoroutineRule
import com.beeline.forecast.TestModels
import com.beeline.forecast.data.api.ApiService
import com.beeline.forecast.data.api.Response
import com.beeline.forecast.data.api.failed
import com.beeline.forecast.data.api.succeeded
import com.beeline.forecast.data.models.ListWeather
import com.beeline.forecast.data.models.Weather
import com.beeline.forecast.data.models.WeatherForecast
import com.beeline.forecast.data.repo.ApiRepo
import com.google.gson.Gson
import junit.framework.Assert
import junit.framework.Assert.assertFalse
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.HttpException
import java.lang.Exception
import java.net.UnknownHostException

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class ApiRepoTest {


    @get:Rule
    var instantTaskExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()


    @Mock
    private lateinit var apiService: ApiService

    @Mock
    private lateinit var apiRepo: ApiRepo


    @Before
    fun before(){
        MockitoAnnotations.initMocks(this)
        apiRepo = ApiRepo(apiService,Dispatchers.Main)
    }


    @Test
    fun `getWeather with exception`(){
        runBlockingTest{
            Mockito.`when`(apiService.getWeather(12)).thenAnswer { UnknownHostException() }
            val answer = apiRepo.getWeather(12) as Response.Error
            assertTrue(answer.failed)
            assertFalse(answer.succeeded)
        }
    }

    @Test
    fun `getWeather with Success`(){
        runBlockingTest{
            val weather = Gson().fromJson<WeatherForecast>(TestModels().weatherString(),WeatherForecast::class.java)
            Mockito.`when`(apiService.getWeather(420006353)).thenReturn(weather)
            val answer = apiRepo.getWeather(420006353) as Response.Success
            assertFalse(answer.failed)
            assertTrue(answer.succeeded)
            assertEquals(answer.data,weather)
        }
    }


    @Test
    fun `getGroupWeather with exception`(){
        runBlockingTest{
             Mockito.`when`(apiService.getGroupWeathers("1,2,3")).thenAnswer { UnknownHostException() }
            val answer = apiRepo.getGroupWeathers(listOf(1,2,3)) as Response.Error
            assertTrue(answer.failed)
            assertFalse(answer.succeeded)
        }
    }
    @Test
    fun `getGroupWeather with Success`(){
        runBlockingTest{
            val weather = Gson().fromJson<ListWeather>(TestModels().groupWeatherString(),ListWeather::class.java)
            Mockito.`when`(apiService.getGroupWeathers("524901,703448,2643743")).thenReturn(weather)
            val answer = apiRepo.getGroupWeathers(listOf(524901,703448,2643743)) as Response.Success
            assertFalse(answer.failed)
            assertTrue(answer.succeeded)
            assertEquals(answer.data,weather)
        }
    }
}