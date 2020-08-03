package com.beeline.forecast.data.repo

import com.beeline.forecast.data.api.Response
import com.beeline.forecast.data.models.WeatherFactory
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

interface FetchService{
    suspend  fun updateCities(): Response<Boolean>
    suspend fun createCity(id: Int?) : Response<Boolean>
    suspend fun createDb() : Response<Boolean>
}

/**
 * Fetch data from internet and record to the roomDb
 */
class FetchRepo(private val apiRepo: ApiRepo, private val roomRepo: RoomRepo, private val ioDispatcher: CoroutineDispatcher) : FetchService{
    override suspend fun updateCities() = withContext(ioDispatcher){
        val ids = roomRepo.getCityIds()
        if (ids is Response.Success){
            val repo = apiRepo.getGroupWeathers(ids.data)
            if (repo is Response.Success){
                repo.data.list.forEach {
                    roomRepo.updateCity(WeatherFactory(it).turnLocal())
                }
                return@withContext Response.Success(true)

            }else if (repo is Response.Error){
                return@withContext Response.Error(repo.exception)
            }
            return@withContext Response.Success(false)
        }else
            return@withContext Response.Success(false)
    }

    override suspend fun createCity(id: Int?) = withContext(ioDispatcher){
        if (roomRepo.checkCity(id)){
            return@withContext Response.Success(false)
        }else {
            val group = apiRepo.getWeather(id)
            if (group is Response.Success) {
                roomRepo.insertCity(WeatherFactory(group.data).turnLocal())
                return@withContext Response.Success(true)
            } else if (group is Response.Error) {
                return@withContext Response.Error(group.exception)
            }
        }
        return@withContext Response.Success(false)
    }

    override suspend fun createDb(): Response<Boolean> = withContext(ioDispatcher){
        val group = apiRepo.getGroupWeathers(listOf(1527534,1528675)) // create roomdb for bishkek and osh cities
        if (group is Response.Success){
            group.data.list.forEach {
                roomRepo.insertCity(WeatherFactory(it).turnLocal())
            }
            return@withContext Response.Success(true)
        }else if (group is Response.Error){
            return@withContext Response.Error(group.exception)
        }
        return@withContext Response.Success(false)
    }
}