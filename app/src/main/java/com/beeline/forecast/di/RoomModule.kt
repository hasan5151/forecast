package com.beeline.forecast.di
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.beeline.forecast.data.repo.RoomRepo
import com.beeline.forecast.data.room.AppDatabase
import org.koin.dsl.module



val roomModule = module {
    single { Room.databaseBuilder(get(), AppDatabase::class.java, "weather").createFromAsset("databases/sqlite.db")

        .build() }
    single { get<AppDatabase>().localWeatherDAO() }
    single { RoomRepo(get(),get()) }
}