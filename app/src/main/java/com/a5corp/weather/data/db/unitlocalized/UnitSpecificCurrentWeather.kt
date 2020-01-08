package com.a5corp.weather.data.db.unitlocalized

interface UnitSpecificCurrentWeather {
    val temperature: Double
    val pressure: Int
    val humidity: Int
    val windSpeed: Double
    val windDirection: Int
    val visibility: Int
    val cityName: String
    val feelsLikeTemperature: Double
    val dt: Long
}