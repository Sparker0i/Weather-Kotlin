package com.a5corp.weather.data.network.response.current

import androidx.room.Entity

@Entity(tableName = "details")
data class Details(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)