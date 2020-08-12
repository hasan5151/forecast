package com.beeline.forecast.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.beeline.forecast.MainCoroutineRule
import com.beeline.forecast.TestModels
import com.beeline.forecast.data.api.ApiService
import com.beeline.forecast.data.api.Response
import com.beeline.forecast.data.models.ListWeather
import com.beeline.forecast.data.repo.ApiRepo
import com.beeline.forecast.data.repo.FetchRepo
import com.beeline.forecast.data.repo.RoomRepo
import com.google.gson.Gson
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class ViewModelTest{

    @get:Rule
    var instantTaskExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Mock
    private lateinit var viewModel: ForecastVM

    @Mock
    private lateinit var apiService: ApiService
    @Mock
    private lateinit var apiRepo: ApiRepo

    @Mock
    private lateinit var roomRepo: RoomRepo

    @Mock
    private lateinit var fetchRepo: FetchRepo

    @Mock
    lateinit var observer: Observer<Response<*>>


    @Before
    fun before(){
        MockitoAnnotations.initMocks(this)
        viewModel = ForecastVM(apiRepo,roomRepo,fetchRepo)
        viewModel.state.observeForever(observer)
    }

    @Test
    fun createDbTest(){
        runBlockingTest {
            viewModel.createDb()
            val weather = Gson().fromJson<ListWeather>(
                TestModels().groupWeatherString(),
                ListWeather::class.java)
            Mockito.`when`(apiService.getGroupWeathers("1527534,1528675")).thenAnswer { weather}

            Mockito.verify(observer).onChanged(Response.Loading)
            Mockito.verify(observer).onChanged(null)
        }
    }
}