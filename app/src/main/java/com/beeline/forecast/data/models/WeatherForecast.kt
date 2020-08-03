package com.beeline.forecast.data.models

import java.io.Serializable

data class WeatherForecast(
    val coord: Coord?,
    val sys : Sys?,
    val base : String?,
    val visibility : String?,
    val wind: Wind?,
    val clouds: Clouds?,
    val weather: List<Weather?>?,
    val main: Main?,
    val dt: Double?,
    val timezone: Double?,
    val id: Int?,
    val name: String?,
    val cod: String?
) : Serializable