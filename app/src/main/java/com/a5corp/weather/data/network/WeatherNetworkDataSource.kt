package com.a5corp.weather.data.network

import androidx.lifecycle.MutableLiveData
import com.a5corp.weather.data.network.response.current.CurrentWeatherResponse

interface WeatherNetworkDataSource {
    val getCurrentWeather: MutableLiveData<CurrentWeatherResponse>

    suspend fun fetchCurrentWeather(location: String)
    suspend fun fetchCurrentWeather(latitude: Double, longitude: Double)
}