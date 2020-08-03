package com.beeline.forecast.data.repo

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.beeline.forecast.data.api.Response
import kotlinx.coroutines.coroutineScope
import org.koin.core.KoinComponent
 import org.koin.core.inject


/**
 *  Auto Refresh
 */
class WeatherWorkManager (context : Context, private val workerParams : WorkerParameters) : CoroutineWorker(context,workerParams), KoinComponent {
    private val fetchRepo: FetchRepo by inject()

    override suspend fun doWork(): Result = coroutineScope{
        val update = fetchRepo.updateCities()
        if (update is Response.Success){
            return@coroutineScope Result.success()
        }else{
            return@coroutineScope Result.failure()
        }
    }
}
