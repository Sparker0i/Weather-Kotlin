package com.a5corp.weather.data.db.unitlocalized

import androidx.room.ColumnInfo

data class MetricCurrentWeather(
    @ColumnInfo(name = "main_temp") override val temperature: Double,
    @ColumnInfo(name = "main_humidity") override val humidity: Int,
    @ColumnInfo(name = "main_pressure") override val pressure: Int,
    @ColumnInfo(name = "wind_speed") override val windSpeed: Double,
    @ColumnInfo(name = "wind_deg") override val windDirection: Int,
    @ColumnInfo(name = "visibility") override val visibility: Int,
    @ColumnInfo(name = "name") override val cityName: String,
    @ColumnInfo(name = "main_feelsLike") override val feelsLikeTemperature: Double
): UnitSpecificCurrentWeather