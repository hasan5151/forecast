package com.beeline.forecast.di
import androidx.room.Room
import com.beeline.forecast.data.repo.RoomRepo
import com.beeline.forecast.data.room.AppDatabase
import org.koin.dsl.module

val roomModule = module {
    single { Room.databaseBuilder(get(), AppDatabase::class.java, "weather").createFromAsset("database/weather.db")
        .build() }
    single { get<AppDatabase>().localWeatherDAO() }
    single { RoomRepo(get(),get()) }
}