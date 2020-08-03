package com.beeline.forecast.di

import com.beeline.forecast.viewmodel.ForecastVM
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModel

val viewModelModule = module {
    viewModel {
        ForecastVM(get(),get(), get())
    }
}