package com.a5corp.weather.data.network.response.current

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "details")
data class Details(
    @PrimaryKey(autoGenerate = false) var id_count: Int = 0,
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)