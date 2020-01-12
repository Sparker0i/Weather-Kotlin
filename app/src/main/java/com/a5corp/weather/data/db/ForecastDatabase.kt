package com.a5corp.weather.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.a5corp.weather.data.network.response.current.*
import com.a5corp.weather.data.network.response.forecast.City
import com.a5corp.weather.data.network.response.forecast.FutureWeatherResponse
import com.a5corp.weather.data.network.response.forecast.WeatherList

@Database(
    entities = [CurrentWeatherResponse::class, Coord::class, Main::class, Sys::class, Wind::class, WeatherDetails::class, FutureWeatherResponse::class, WeatherList::class, City::class],
    version = 1
)
abstract class ForecastDatabase: RoomDatabase() {
    abstract fun currentWeatherDao(): CurrentWeatherDao

    companion object {
        @Volatile private var instance: ForecastDatabase? = null
        private var LOCK = Any()

        operator fun invoke(context: Context) = instance
            ?: synchronized(LOCK) {
            instance
                ?: buildDatabase(
                    context
                ).also { instance = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext, ForecastDatabase::class.java, "forecast.db")
                .build()
    }
}