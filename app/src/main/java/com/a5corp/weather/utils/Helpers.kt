package com.a5corp.weather.utils

import org.threeten.bp.Instant
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime

fun zonedDateTime(dt: Long): ZonedDateTime {
    val instant = Instant.ofEpochSecond(dt)
    val zoneId = ZoneId.of("Z")
    return ZonedDateTime.ofInstant(instant, zoneId)
}