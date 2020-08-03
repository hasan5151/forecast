package com.beeline.forecast.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.beeline.forecast.data.room.dao.LocalWeatherDAO
import com.beeline.forecast.data.room.entity.LocalWeather

@Database(entities = [LocalWeather::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun localWeatherDAO(): LocalWeatherDAO
}
