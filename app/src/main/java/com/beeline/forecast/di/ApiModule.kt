package com.beeline.forecast.di

import com.beeline.forecast.BuildConfig.APIURL
import com.beeline.forecast.data.api.ApiService
import com.beeline.forecast.data.api.OauthInterceptor
import com.beeline.forecast.data.repo.ApiRepo
import com.beeline.forecast.data.repo.FetchRepo
import com.beeline.forecast.utils.AllCities
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val apiModule = module {
        factory {    OkHttpClient.Builder()
                .addInterceptor(OauthInterceptor(get()))
                .build() }
        single  { provideApiService(get()) }
        single { provideDispatcher() }
        single { ApiRepo(get(),get()) }
        single { AllCities() }
        factory { FetchRepo(get(),get(),get()) }
}

fun provideApiService(okHttpClient: OkHttpClient): ApiService {
    val retrofit  = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(APIURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    return retrofit.create(ApiService::class.java)
}

fun provideDispatcher(): CoroutineDispatcher =  Dispatchers.IO

