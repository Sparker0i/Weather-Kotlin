package com.a5corp.weather.data.repository

import androidx.lifecycle.LiveData
import com.a5corp.weather.data.db.CurrentWeatherDao
import com.a5corp.weather.data.db.unitlocalized.UnitSpecificCurrentWeather
import com.a5corp.weather.data.network.WeatherNetworkDataSource
import com.a5corp.weather.data.network.response.current.CurrentWeatherResponse
import com.a5corp.weather.internal.UnitSystem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.ZonedDateTime
import java.util.*

class ForecastRepositoryImpl(
    private val currentWeatherDao: CurrentWeatherDao,
    private val weatherNetworkDataSource: WeatherNetworkDataSource
) : ForecastRepository {

    init {
        weatherNetworkDataSource.getCurrentWeather.observeForever{ newCurrentWeather ->
            persistFetchedCurrentWeather(newCurrentWeather)
        }
    }

    override suspend fun getCurrentWeather(metric: Boolean): LiveData<out UnitSpecificCurrentWeather> {
        return withContext(Dispatchers.IO) {
            initWeatherData(metric)
            return@withContext if (metric) currentWeatherDao.getWeatherMetric()
            else currentWeatherDao.getWeatherImperial()
        }
    }

    private fun persistFetchedCurrentWeather(fetchedWeather: CurrentWeatherResponse) {
        GlobalScope.launch(Dispatchers.IO) {
            currentWeatherDao.upsert(fetchedWeather)
        }
    }

    private suspend fun initWeatherData(metric: Boolean) {
        if (isFetchCurrentNeeded(ZonedDateTime.now().minusHours(1)))
            fetchCurrentWeather(metric)
    }

    private suspend fun fetchCurrentWeather(metric: Boolean) {
        val units = if (metric) UnitSystem.METRIC.name.toLowerCase(Locale.getDefault()) else UnitSystem.IMPERIAL.name.toLowerCase(Locale.getDefault())
        weatherNetworkDataSource.fetchCurrentWeather("Bangalore", units)
    }

    private fun isFetchCurrentNeeded(lastFetchTime: ZonedDateTime): Boolean {
        val hourAgo = ZonedDateTime.now().minusMinutes(30)
        return lastFetchTime.isBefore(hourAgo)
    }
}