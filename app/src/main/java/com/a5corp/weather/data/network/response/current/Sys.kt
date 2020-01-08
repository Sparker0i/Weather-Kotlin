package com.a5corp.weather.data.network.response.current

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sys")
data class Sys(
    val type: Int,
    val country: String,
    val sunrise: Int,
    val sunset: Int
) {
    @PrimaryKey(autoGenerate = false) var id_count = 0
}