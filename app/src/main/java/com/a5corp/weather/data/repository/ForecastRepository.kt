package com.a5corp.weather.data.repository

import androidx.lifecycle.LiveData
import com.a5corp.weather.data.db.unitlocalized.UnitSpecificCurrentWeather
import com.a5corp.weather.data.network.response.current.Coord

interface ForecastRepository {
    suspend fun getCurrentWeather(metric: Boolean): LiveData<out UnitSpecificCurrentWeather>
    suspend fun getWeatherLocation(): LiveData<Coord>
}