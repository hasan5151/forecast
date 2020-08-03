package com.beeline.forecast.di

import com.beeline.forecast.utils.Prefs
import org.koin.dsl.module

val prefModule = module {
    single{ Prefs(get()) }
}
