package com.a5corp.weather.data.provider

import com.a5corp.weather.data.db.unitlocalized.UnitSpecificCurrentWeather

class LocationProviderImpl : LocationProvider {
    override suspend fun hasLocationChanged(lastWeatherLocation: UnitSpecificCurrentWeather): Boolean {
        return true
    }

    override suspend fun getPreferredLocationString(): String {
        return "Bangalore"
    }
}