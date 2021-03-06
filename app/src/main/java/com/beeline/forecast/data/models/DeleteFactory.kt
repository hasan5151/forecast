package com.beeline.forecast.data.models

import com.beeline.forecast.data.room.entity.LocalWeather

/**
 * Factory for `LocalWeather` to `DeleteModel`
 */
class DeleteFactory(private val localWeather: LocalWeather){
    fun turnDelete(): DeleteModel = DeleteModel(false, localWeather)
}