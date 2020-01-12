package com.a5corp.weather.utils

import android.R
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred
import org.threeten.bp.Instant
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime
import java.util.*


fun zonedDateTime(dt: Long): ZonedDateTime {
    val instant = Instant.ofEpochSecond(dt)
    val zoneId = ZoneId.of("Z")
    return ZonedDateTime.ofInstant(instant, zoneId)
}

fun <T> Task<T>.asDeferred(): Deferred<T> {
    val deferred = CompletableDeferred<T>()

    this.addOnSuccessListener { result ->
        deferred.complete(result)
    }

    this.addOnFailureListener{ exception ->
        deferred.completeExceptionally(exception)
    }

    return deferred
}

fun setWeatherIcon(id: Int): String {
    val newId = id / 100
    var icon = ""
    if (newId * 100 == 800) {
        val hourOfDay: Int = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        icon = if (hourOfDay in 7..18) {
            AppConstants.ICON_WEATHER_SUNNY
        } else {
            AppConstants.ICON_WEATHER_CLEAR_NIGHT
        }
    } else {
        when (newId) {
            2 -> icon = AppConstants.ICON_WEATHER_THUNDER
            3 -> icon = AppConstants.ICON_WEATHER_DRIZZLE
            7 -> icon = AppConstants.ICON_WEATHER_FOGGY
            8 -> icon = AppConstants.ICON_WEATHER_CLOUDY
            6 -> icon = AppConstants.ICON_WEATHER_SNOWY
            5 -> icon = AppConstants.ICON_WEATHER_RAINY
        }
    }
    return icon
}