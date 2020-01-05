package com.a5corp.weather.data.network.response.current

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wind")
data class Wind(
    val speed: Double,
    val deg: Int
) {
    @PrimaryKey(autoGenerate = false) var id = 0
}