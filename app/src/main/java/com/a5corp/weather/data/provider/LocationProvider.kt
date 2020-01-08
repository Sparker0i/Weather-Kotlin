package com.a5corp.weather.data.provider

import com.a5corp.weather.data.db.unitlocalized.UnitSpecificCurrentWeather

interface LocationProvider {
    suspend fun hasLocationChanged(lastWeatherLocation: UnitSpecificCurrentWeather): Boolean
    suspend fun getPreferredLocationString(): String
}