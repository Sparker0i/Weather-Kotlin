package com.a5corp.weather.data.repository

import androidx.lifecycle.LiveData
import com.a5corp.weather.data.db.CurrentWeatherDao
import com.a5corp.weather.data.db.unitlocalized.UnitSpecificCurrentWeather
import com.a5corp.weather.data.network.WeatherNetworkDataSource
import com.a5corp.weather.data.network.response.current.Coord
import com.a5corp.weather.data.network.response.current.CurrentWeatherResponse
import com.a5corp.weather.data.provider.LocationProvider
import com.a5corp.weather.data.provider.PreferenceProvider
import com.a5corp.weather.internal.UnitSystem
import com.a5corp.weather.utils.zonedDateTime
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.ZonedDateTime
import java.util.*

const val USE_DEVICE_LOCATION = "USE_DEVICE_LOCATION"
class ForecastRepositoryImpl(
    private val currentWeatherDao: CurrentWeatherDao,
    private val weatherNetworkDataSource: WeatherNetworkDataSource,
    private val locationProvider: LocationProvider,
    private val preferenceProvider: PreferenceProvider
) : ForecastRepository {

    init {
        weatherNetworkDataSource.fetchedCurrentWeather.observeForever{ newCurrentWeather ->
            persistFetchedCurrentWeather(newCurrentWeather)
        }
    }

    override suspend fun getCurrentWeather(metric: Boolean): LiveData<out UnitSpecificCurrentWeather> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun getCurrentWeatherResponse(metric: Boolean): LiveData<out CurrentWeatherResponse> {
        return withContext(Dispatchers.IO) {
            initWeatherData(metric)
            return@withContext currentWeatherDao.getWeather()
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
        val lastWeatherLocation = currentWeatherDao.getWeather()

        if (lastWeatherLocation.value == null
            || locationProvider.hasLocationChanged(lastWeatherLocation.value!!)) {
            fetchCurrentWeather(metric)
            return
        }

        if (isFetchCurrentNeeded(zonedDateTime(lastWeatherLocation.value!!.dt!!)))
            fetchCurrentWeather(metric)
    }

    private suspend fun fetchCurrentWeather(metric: Boolean) {
        val units = if (metric) UnitSystem.METRIC.name.toLowerCase(Locale.getDefault()) else UnitSystem.IMPERIAL.name.toLowerCase(Locale.getDefault())
        if (preferenceProvider.preferences.getBoolean(USE_DEVICE_LOCATION, false))
            weatherNetworkDataSource.fetchCurrentWeather(locationProvider.getDeviceLocation().latitude, locationProvider.getDeviceLocation().longitude, units)
        else
            weatherNetworkDataSource.fetchCurrentWeather(locationProvider.getCityName(), units)
    }

    private fun isFetchCurrentNeeded(lastFetchTime: ZonedDateTime): Boolean {
        val hourAgo = ZonedDateTime.now().minusMinutes(30)
        return lastFetchTime.isBefore(hourAgo)
    }
}