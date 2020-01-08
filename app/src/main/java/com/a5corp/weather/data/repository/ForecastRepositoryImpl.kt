package com.a5corp.weather.data.repository

import androidx.lifecycle.LiveData
import com.a5corp.weather.data.db.CurrentWeatherDao
import com.a5corp.weather.data.db.unitlocalized.UnitSpecificCurrentWeather
import com.a5corp.weather.data.network.WeatherNetworkDataSource
import com.a5corp.weather.data.network.response.current.Coord
import com.a5corp.weather.data.network.response.current.CurrentWeatherResponse
import com.a5corp.weather.data.provider.LocationProvider
import com.a5corp.weather.internal.UnitSystem
import com.a5corp.weather.utils.zonedDateTime
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.ZonedDateTime
import java.util.*

class ForecastRepositoryImpl(
    private val currentWeatherDao: CurrentWeatherDao,
    private val weatherNetworkDataSource: WeatherNetworkDataSource,
    private val locationProvider: LocationProvider
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

    override suspend fun getWeatherLocation(): LiveData<Coord> {
        return withContext(Dispatchers.IO) {
            return@withContext currentWeatherDao.getLocation()
        }
    }

    private fun persistFetchedCurrentWeather(fetchedWeather: CurrentWeatherResponse) {
        GlobalScope.launch(Dispatchers.IO) {
            currentWeatherDao.upsert(fetchedWeather)
        }
    }

    private suspend fun initWeatherData(metric: Boolean) {
        val lastWeatherLocation = if (metric) currentWeatherDao.getWeatherMetric().value else currentWeatherDao.getWeatherImperial().value

        if (lastWeatherLocation == null
            || locationProvider.hasLocationChanged(lastWeatherLocation)) {
            fetchCurrentWeather(metric)
            return
        }

        if (isFetchCurrentNeeded(zonedDateTime(lastWeatherLocation.dt)))
            fetchCurrentWeather(metric)
    }

    private suspend fun fetchCurrentWeather(metric: Boolean) {
        val units = if (metric) UnitSystem.METRIC.name.toLowerCase(Locale.getDefault()) else UnitSystem.IMPERIAL.name.toLowerCase(Locale.getDefault())
        weatherNetworkDataSource.fetchCurrentWeather(locationProvider.getPreferredLocationString(), units)
    }

    private fun isFetchCurrentNeeded(lastFetchTime: ZonedDateTime): Boolean {
        val hourAgo = ZonedDateTime.now().minusMinutes(30)
        return lastFetchTime.isBefore(hourAgo)
    }
}