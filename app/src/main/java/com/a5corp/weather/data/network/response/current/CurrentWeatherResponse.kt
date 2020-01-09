package com.a5corp.weather.data.network.response.current

import androidx.room.*
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken

@Entity(tableName = "weather_response")
data class CurrentWeatherResponse(
    @PrimaryKey(autoGenerate = false) var id_count: Int = 0,
    @Embedded(prefix = "coord_") val coord: Coord,
    @Embedded(prefix = "details_") @SerializedName("weather") val details: ArrayList<Details>,
    val base: String,
    @Embedded(prefix = "main_") val main: Main,
    val visibility: Int,
    @Embedded(prefix = "wind_") val wind: Wind,
    val dt: Long,
    @Embedded(prefix = "sys_") val sys: Sys,
    val timezone: Int,
    val name: String,
    val cod: Int
)

object DetailTypeConverters {
    val gson = Gson()

    @TypeConverter @JvmStatic fun fromList(value: ArrayList<Details>): String {
        return gson.toJson(value)
    }

    @TypeConverter @JvmStatic fun toList(value: String): ArrayList<Details> {
        val type = object : TypeToken<ArrayList<Details>>() {}.type
        return gson.fromJson(value, type)
    }
}