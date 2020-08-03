package com.beeline.forecast.di

import com.beeline.forecast.utils.WorkManagerUtil
import org.koin.dsl.module

val workManagerModule = module {
    single { WorkManagerUtil(get(),get()) }
}