package com.a5corp.weather.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.a5corp.weather.data.network.response.current.CurrentWeatherResponse
import com.a5corp.weather.internal.NoConnectivityException

class WeatherNetworkDataSourceImpl(
    private val openWeatherMapApiService: OpenWeatherMapApiService
) : WeatherNetworkDataSource {
    override val getCurrentWeather = MutableLiveData<CurrentWeatherResponse>()

    override suspend fun fetchCurrentWeather(location: String, units: String) {
        try {
            val fetchCurrentWeather = openWeatherMapApiService
                .getCurrentWeather(location, units)
                .await()

            getCurrentWeather.postValue(fetchCurrentWeather)
        }
        catch (e: NoConnectivityException) {
            Log.e("Connectivity", "No Internet Connection", e)
        }
    }

    override suspend fun fetchCurrentWeather(latitude: Double, longitude: Double, units: String) {
        try {
            val fetchCurrentWeather = openWeatherMapApiService
                .getCurrentWeather(latitude, longitude, units)
                .await()

            getCurrentWeather.postValue(fetchCurrentWeather)
        }
        catch (e: NoConnectivityException) {
            Log.e("Connectivity", "No Internet Connection", e)
        }
    }
}