package com.beeline.forecast.data.api

import com.beeline.forecast.data.models.ListWeather
import com.beeline.forecast.data.models.WeatherForecast
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("group")
    suspend fun getGroupWeathers(@Query("id") id : String?): ListWeather

    @GET("weather")
    suspend fun getWeather(@Query("id") id : Int?): WeatherForecast
}