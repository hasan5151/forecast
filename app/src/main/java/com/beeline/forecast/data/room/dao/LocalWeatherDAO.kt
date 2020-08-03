package com.beeline.forecast.data.room.dao

import androidx.paging.DataSource
import androidx.room.*
import com.beeline.forecast.data.room.entity.LocalWeather

@Dao
interface LocalWeatherDAO {

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCity(localWeather: LocalWeather)

    @Update
    suspend fun updateCity(localWeather: LocalWeather)

    @Delete
    suspend fun deleteCity(localWeather: LocalWeather)

    @Query("Select  * from LocalWeather")
    fun  getAll() : DataSource.Factory<Int, LocalWeather>

    @Query("Select  * from LocalWeather where id=:id")
    suspend fun  getCityById(id : Int?) : LocalWeather

    @Query("Select COUNT(id) from LocalWeather where id=:id")
    suspend fun  checkCity(id : Int?) : Boolean

    @Query("Select id from LocalWeather")
    suspend fun  getCityIds() : List<Int>?
}