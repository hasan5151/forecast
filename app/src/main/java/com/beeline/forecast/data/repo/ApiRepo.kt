package com.beeline.forecast.data.repo

 import com.beeline.forecast.data.api.ApiService
import com.beeline.forecast.data.api.Response
import com.beeline.forecast.data.models.ListWeather
import com.beeline.forecast.data.models.WeatherForecast
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

interface ApiRepoService{
    suspend fun getWeather(id : Int?) : Response<WeatherForecast>
    suspend fun getGroupWeathers(ids : List<Int>?) : Response<ListWeather>
}

/**
 * Repository of Api
 */

class ApiRepo(private val apiService: ApiService, private val ioDispatcher: CoroutineDispatcher) : ApiRepoService{
    override suspend fun getWeather(id : Int?): Response<WeatherForecast> = withContext(ioDispatcher) {
         try {
            val task = apiService.getWeather(id)
            if (task!=null) {
                return@withContext Response.Success(task)
            } else {
                return@withContext Response.Error(Exception("Task not found!"))
            }
        } catch (e: Exception) {
            return@withContext Response.Error(e)
        }

    }

    override suspend fun getGroupWeathers(ids : List<Int>?): Response<ListWeather> = withContext(ioDispatcher) {
        var newString =""
        ids?.forEachIndexed {index,item->
            newString += if(index!=ids.size-1)
                "$item,"
            else
                "$item"
        }

        try {
            val task = apiService.getGroupWeathers(newString)
            if (task!=null) {
                return@withContext Response.Success(task)
            } else {
                return@withContext Response.Error(Exception("Task not found!"))
            }
        } catch (e: Exception) {
            return@withContext Response.Error(e)
        }

    }

}