package com.a5corp.weather.utils

import com.a5corp.weather.BuildConfig

object AppConstants {
    var OWM_API_KEY = BuildConfig.OWM_API_KEY
    var OWM_API_URL = BuildConfig.OWM_API_URL

    //API Parameters
    const val QUERY_PARAM = "q"
    const val FORMAT_PARAM = "mode"
    const val FORMAT_VALUE = "json"
    const val UNITS_PARAM = "units"
    const val DAYS_PARAM = "cnt"
    const val API_KEY_PARAM = "apikey"
    const val LATITUDE_PARAM = "lat"
    const val LONGITUDE_PARAM = "lon"

    //Weather Icons - Parameters
    const val ICON_HUMIDITY = "\uF07a"
    const val ICON_PRESSURE = "\uF079"
    const val ICON_SPEED = "\uF050"
    const val ICON_SUNRISE = "\uF051"
    const val ICON_SUNSET = "\uF052"

    //Weather Icons - Condition
    const val ICON_WEATHER_SUNNY = "\uF00d"
    const val ICON_WEATHER_CLEAR_NIGHT = "\uF02e"
    const val ICON_WEATHER_FOGGY = "\uF014"
    const val ICON_WEATHER_CLOUDY = "\uF013"
    const val ICON_WEATHER_RAINY = "\uF019"
    const val ICON_WEATHER_SNOWY = "\uF01b"
    const val ICON_WEATHER_THUNDER = "\uF01e"
    const val ICON_WEATHER_DRIZZLE = "\uF01a"
}