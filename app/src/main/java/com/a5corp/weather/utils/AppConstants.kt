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
}