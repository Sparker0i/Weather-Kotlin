package com.a5corp.weather.utils

import com.google.android.gms.tasks.Task
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred
import org.threeten.bp.Instant
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime

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