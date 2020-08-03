package com.beeline.forecast.data.models

import com.beeline.forecast.data.room.entity.LocalWeather

/**
 * Created for selection
 */
data class DeleteModel(var selected: Boolean, val localWeather: LocalWeather)