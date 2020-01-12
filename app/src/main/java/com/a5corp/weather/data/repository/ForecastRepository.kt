package com.a5corp.weather.data.repository

import androidx.lifecycle.LiveData
import com.a5corp.weather.data.db.unitlocalized.UnitSpecificCurrentWeather
import com.a5corp.weather.data.network.response.current.Coord
import com.a5corp.weather.data.network.response.current.CurrentWeatherResponse

interface ForecastRepository {
    suspend fun getCurrentWeather(metric: Boolean): LiveData<out UnitSpecificCurrentWeather>
    suspend fun getCurrentWeatherResponse(metric: Boolean): LiveData<out CurrentWeatherResponse>
    suspend fun getWeatherLocation(): LiveData<Coord>
}