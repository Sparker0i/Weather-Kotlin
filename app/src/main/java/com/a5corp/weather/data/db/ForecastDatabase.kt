package com.a5corp.weather.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.a5corp.weather.data.network.response.current.*

@Database(
    entities = [CurrentWeatherResponse::class, Coord::class, Main::class, Sys::class, Wind::class, Details::class],
    version = 1
)
@TypeConverters(DetailTypeConverters::class)
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