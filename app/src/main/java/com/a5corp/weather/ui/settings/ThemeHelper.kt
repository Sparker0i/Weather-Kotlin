package com.a5corp.weather.ui.settings

import android.os.Build
import androidx.appcompat.app.AppCompatDelegate

object ThemeHelper {
    const val LIGHT_MODE = "light"
    const val DARK_MODE = "dark"
    const val FOLLOW_SYSTEM = "follow_system"
    const val FOLLOW_BATTERY_SAVER = "follow_battery_saver"

    fun applyTheme(theme: String) {
        val mode = when(theme) {
            LIGHT_MODE -> AppCompatDelegate.MODE_NIGHT_NO
            DARK_MODE -> AppCompatDelegate.MODE_NIGHT_YES
            FOLLOW_SYSTEM -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
            FOLLOW_BATTERY_SAVER -> AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY
            else -> AppCompatDelegate.MODE_NIGHT_NO
        }
        AppCompatDelegate.setDefaultNightMode(mode)
    }
}