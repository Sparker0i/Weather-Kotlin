package com.a5corp.weather.data.network.response.current

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "coordinates")
data class Coord(
    val lat: Double,
    val lon: Double
) {
    @PrimaryKey(autoGenerate = false) var id_count = 0
}