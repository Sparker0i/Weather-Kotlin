package com.a5corp.weather.data.provider

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

open class PreferenceProvider(context: Context) {
    private val appContext = context.applicationContext
    val preferences: SharedPreferences
        get() = PreferenceManager.getDefaultSharedPreferences(appContext)
}