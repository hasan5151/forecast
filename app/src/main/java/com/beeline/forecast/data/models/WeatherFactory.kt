package com.beeline.forecast.data.models

import com.beeline.forecast.data.room.entity.LocalWeather

/**
 * factory for `WeatherForecast` to `LocalWeather`
 */
class WeatherFactory(private val weather: WeatherForecast){

    fun turnLocal() : LocalWeather = LocalWeather(
            weather.id,
            weather.name,
            System.currentTimeMillis(),
            weather.main?.temp?.toInt(),
            weather.main?.feels_like?.toInt(),
            weather.weather?.get(0)?.description,
            "a"+weather.weather?.get(0)?.icon)
}