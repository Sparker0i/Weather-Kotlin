package com.a5corp.weather.data.network.response.current

import androidx.room.*
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken

@Entity(tableName = "weather_response")
data class CurrentWeatherResponse(
    @PrimaryKey(autoGenerate = false) var id: Int = 0,
    @Embedded(prefix = "coord_") val coord: Coord,
    //@Embedded(prefix = "details_") @SerializedName("weather") val details: List<Details>,
    val base: String,
    @Embedded(prefix = "main_") val main: Main,
    val visibility: Int,
    @Embedded(prefix = "wind_") val wind: Wind,
    val dt: Int,
    @Embedded(prefix = "sys_") val sys: Sys,
    val timezone: Int,
    val name: String,
    val cod: Int
)

class DetailTypeConverters {
    val gson = Gson()

    @TypeConverter fun fromList(value: List<Details>): String {
        return gson.toJson(value)
    }

    @TypeConverter fun toList(value: String): List<Details> {
        val type = object : TypeToken<List<Details>>() {}.type
        return gson.fromJson(value, type)
    }
}