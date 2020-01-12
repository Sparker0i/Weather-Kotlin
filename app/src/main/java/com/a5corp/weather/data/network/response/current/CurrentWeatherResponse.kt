package com.a5corp.weather.data.network.response.current

import androidx.room.*
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken

@Entity(tableName = "weather_response")
@TypeConverters(DetailTypeConverters::class)
data class CurrentWeatherResponse(
    @PrimaryKey(autoGenerate = false) var id_count: Int = 0,
    @Embedded(prefix = "coord_") val coord: Coord,
    val weather: List<WeatherDetails>?,
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

    @TypeConverter @JvmStatic fun fromList(value: List<WeatherDetails>?): String? {
        return gson.toJson(value)
    }

    @TypeConverter @JvmStatic fun toList(value: String?): List<WeatherDetails>? {
        val type = object : TypeToken<List<WeatherDetails>>() {}.type
        return gson.fromJson(value, type)
    }
}

@Entity(tableName = "weather_coordinates")
open class Coord(
    @PrimaryKey(autoGenerate = false) open var id_count: Int = 0,
    open var lat: Double,
    open var lon: Double
)

@Entity(tableName = "weather_details")
open class WeatherDetails(
    @PrimaryKey(autoGenerate = false) open var id_count: Int = 0,
    open val id: Int,
    open val main: String,
    open val description: String,
    open val icon: String
)

@Entity(tableName = "weather_main")
open class Main(
    @PrimaryKey(autoGenerate = false) open var id_count: Int = 0,
    open var temp: Double,
    @SerializedName("feels_like") open var feelsLike: Double,
    @SerializedName("temp_min") open var tempMin: Double,
    @SerializedName("temp_max") open var tempMax: Double,
    open var pressure: Int,
    open var humidity: Int
)

@Entity(tableName = "weather_sys")
data class Sys(
    val type: Int,
    val country: String,
    val sunrise: Int,
    val sunset: Int
) {
    @PrimaryKey(autoGenerate = false) var id_count = 0
}

@Entity(tableName = "weather_wind")
open class Wind(
    @PrimaryKey(autoGenerate = false) open var id_count: Int = 0,
    open var speed: Double,
    open var deg: Int
)