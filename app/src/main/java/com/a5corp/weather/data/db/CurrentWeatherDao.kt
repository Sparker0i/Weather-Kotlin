package com.a5corp.weather.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.a5corp.weather.data.db.unitlocalized.ImperialCurrentWeather
import com.a5corp.weather.data.db.unitlocalized.MetricCurrentWeather
import com.a5corp.weather.data.network.response.current.Coord
import com.a5corp.weather.data.network.response.current.CurrentWeatherResponse

@Dao
interface CurrentWeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE) fun upsert(weatherEntry: CurrentWeatherResponse)

    @Query("SELECT ID_COUNT, LAT, LON FROM WEATHER_COORDINATES WHERE ID_COUNT = 0") fun getLocation(): LiveData<Coord>
    @Query("SELECT * FROM WEATHER_RESPONSE WHERE ID_COUNT = 0") fun getWeatherMetric(): LiveData<MetricCurrentWeather>
    @Query("SELECT * FROM WEATHER_RESPONSE WHERE ID_COUNT = 0") fun getWeather(): LiveData<CurrentWeatherResponse>
    @Query("SELECT * FROM WEATHER_RESPONSE WHERE ID_COUNT = 0") fun getWeatherImperial(): LiveData<ImperialCurrentWeather>
}