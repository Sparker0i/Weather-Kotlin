package com.a5corp.weather.data.provider

import android.location.Location
import com.a5corp.weather.data.network.response.current.CurrentWeatherResponse

interface LocationProvider {
    suspend fun hasLocationChanged(lastWeatherLocation: CurrentWeatherResponse): Boolean
    suspend fun getCityName(): String
    suspend fun getDeviceLocation(): Location
}