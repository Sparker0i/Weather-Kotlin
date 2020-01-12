package com.a5corp.weather.data.network

import androidx.lifecycle.MutableLiveData
import com.a5corp.weather.data.network.response.current.CurrentWeatherResponse
import com.a5corp.weather.data.network.response.forecast.FutureWeatherResponse

interface WeatherNetworkDataSource {
    val fetchedCurrentWeather: MutableLiveData<CurrentWeatherResponse>
    val fetchedFutureWeather: MutableLiveData<FutureWeatherResponse>

    suspend fun fetchCurrentWeather(location: String, units: String)
    suspend fun fetchCurrentWeather(latitude: Double, longitude: Double, units: String)
    suspend fun fetchFutureWeather(location: String, units: String, days: Int)
    suspend fun fetchFutureWeather(latitude: Double, longitude: Double, units: String, days: Int)
}