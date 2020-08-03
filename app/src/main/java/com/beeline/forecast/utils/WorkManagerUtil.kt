package com.beeline.forecast.utils

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.beeline.forecast.data.repo.WeatherWorkManager
import com.google.common.util.concurrent.ListenableFuture
import java.util.concurrent.ExecutionException
import java.util.concurrent.TimeUnit

/**
 * Schedule WorkManager.
 * @see WeatherWorkManager
 */
class WorkManagerUtil(private val context: Context, private val prefs : Prefs) {
    private val weatherWorkStr = "weatherWork"

    fun scheduleWork() {
        val weatherWorkBuilder: PeriodicWorkRequest.Builder = PeriodicWorkRequest.Builder(
            WeatherWorkManager::class.java, prefs.getLastUpdate().toLong(),
            TimeUnit.MINUTES
        )
        val weatherWork = weatherWorkBuilder.build()
        val instance = WorkManager.getInstance(context)
        instance.enqueueUniquePeriodicWork(weatherWorkStr, ExistingPeriodicWorkPolicy.KEEP, weatherWork)
    }

    private fun isWorkScheduled(tag: String): Boolean {
        val instance = WorkManager.getInstance(context)
        val statuses: ListenableFuture<List<WorkInfo>> =
            instance.getWorkInfosForUniqueWork(tag)
        return try {
            var running = false
            val workInfoList: List<WorkInfo> = statuses.get()
            for (workInfo in workInfoList) {
                val state = workInfo.state
                running = state == WorkInfo.State.RUNNING || state == WorkInfo.State.ENQUEUED
            }
            running
        } catch (e: ExecutionException) {
            e.printStackTrace()
            false
        } catch (e: InterruptedException) {
            e.printStackTrace()
            false
        }
    }

    fun checkWorkManager(){
        if(!isWorkScheduled(weatherWorkStr)) { // check if your work is not already scheduled
            println("work manager doesnt work")
            scheduleWork(); // schedule your work
        }else{
            println("work manager working")
        }
    }
}