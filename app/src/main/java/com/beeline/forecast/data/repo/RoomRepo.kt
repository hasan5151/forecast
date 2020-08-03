package com.beeline.forecast.data.repo

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.beeline.forecast.data.api.Response
import com.beeline.forecast.data.room.dao.LocalWeatherDAO
import com.beeline.forecast.data.room.entity.LocalWeather
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

interface RoomService{
    suspend fun insertCity(localWeather: LocalWeather)
    suspend fun updateCity(localWeather: LocalWeather)
    fun getAll() :LiveData<PagedList<LocalWeather>>
    suspend fun  getCityById(id : Int?) : Response<LocalWeather>
    suspend fun  checkCity(id : Int?) : Boolean
    suspend fun  getCityIds() : Response<List<Int>?>
    suspend fun  deleteCity(localWeather: LocalWeather)
}

/**
 * Repository of RoomDb
 */
class RoomRepo(private val localWeatherDAO: LocalWeatherDAO, private val ioDispatcher: CoroutineDispatcher) : RoomService{
    override suspend fun insertCity(localWeather: LocalWeather) = withContext(ioDispatcher){
        localWeatherDAO.insertCity(localWeather)
    }

    override suspend fun updateCity(localWeather: LocalWeather) = withContext(ioDispatcher) {
        localWeatherDAO.updateCity(localWeather)
    }

    override fun getAll(): LiveData<PagedList<LocalWeather>> =
    LivePagedListBuilder<Int, LocalWeather>(
    localWeatherDAO.getAll(), 10
    ).build()

    override suspend fun getCityById(id: Int?): Response<LocalWeather> = withContext(ioDispatcher){
        return@withContext Response.Success(localWeatherDAO.getCityById(id))
    }

    override suspend fun checkCity(id: Int?): Boolean = withContext(ioDispatcher){
        return@withContext localWeatherDAO.checkCity(id)
    }

    override suspend fun getCityIds(): Response<List<Int>?> = withContext(ioDispatcher) {
        return@withContext Response.Success(localWeatherDAO.getCityIds())
    }

    override suspend fun deleteCity(localWeather: LocalWeather) = withContext(ioDispatcher) {
        return@withContext localWeatherDAO.deleteCity(localWeather)
    }
}