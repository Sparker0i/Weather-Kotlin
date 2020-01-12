package com.a5corp.weather.data.network

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.a5corp.weather.data.network.response.current.CurrentWeatherResponse
import com.a5corp.weather.data.network.response.forecast.FutureWeatherResponse
import com.a5corp.weather.internal.NoConnectivityException
import com.google.gson.Gson

class WeatherNetworkDataSourceImpl(
    private val openWeatherMapApiService: OpenWeatherMapApiService
) : WeatherNetworkDataSource {
    override val fetchedCurrentWeather = MutableLiveData<CurrentWeatherResponse>()
    override val fetchedFutureWeather = MutableLiveData<FutureWeatherResponse>()

    override suspend fun fetchCurrentWeather(location: String, units: String) {
        try {
            val fetchCurrentWeather = openWeatherMapApiService
                .getCurrentWeather(location, units)
                .await()

            fetchedCurrentWeather.postValue(fetchCurrentWeather)
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

            fetchedCurrentWeather.postValue(fetchCurrentWeather)
        }
        catch (e: NoConnectivityException) {
            Log.e("Connectivity", "No Internet Connection", e)
        }
    }

    override suspend fun fetchFutureWeather(location: String, units: String, days: Int) {
        try {
            val fetchFutureWeather = openWeatherMapApiService
                .getFutureWeather(location, units, days)
                .await()

            fetchedFutureWeather.postValue(fetchFutureWeather)
        }
        catch (e: NoConnectivityException) {
            Log.e("Connectivity", "No Internet Connection", e)
        }
    }

    override suspend fun fetchFutureWeather(latitude: Double, longitude: Double, units: String, days: Int) {
        try {
            val fetchFutureWeather = openWeatherMapApiService
                .getFutureWeather(latitude, longitude, units, days)
                .await()

            fetchedFutureWeather.postValue(fetchFutureWeather)
        }
        catch (e: NoConnectivityException) {
            Log.e("Connectivity", "No Internet Connection", e)
        }
    }
}