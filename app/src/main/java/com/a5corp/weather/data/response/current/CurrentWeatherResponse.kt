package com.a5corp.weather.data.response.current

import com.google.gson.annotations.SerializedName

data class CurrentWeatherResponse(
    val coord: Coord,
    @SerializedName("weather") val details: List<Details>,
    val base: String,
    val main: Main,
    val visibility: Int,
    val wind: Wind,
    val clouds: Clouds,
    val dt: Int,
    val sys: Sys,
    val timezone: Int,
    val id: Int,
    val name: String,
    val cod: Int
)