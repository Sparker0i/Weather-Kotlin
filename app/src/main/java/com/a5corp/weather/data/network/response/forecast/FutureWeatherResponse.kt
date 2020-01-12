package com.a5corp.weather.data.network.response.forecast


import androidx.room.*
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken

@Entity(tableName = "future_weather")
@TypeConverters(FutureTypeConverters::class)
data class FutureWeatherResponse(
    @PrimaryKey(autoGenerate = false) val id_count: Int = 1,
    @Embedded(prefix = "city_") val city: City,
    val cod: String,
    val list: ArrayList<WeatherList>
)

@Entity(tableName = "future_list", indices = [Index(value = ["dt"], unique = true)])
@TypeConverters(FutureTypeConverters::class)
data class WeatherList(
    @PrimaryKey(autoGenerate = false) var id_count: Int = 1,
    val dt: Int,
    @Embedded(prefix = "main_") val main: Main,
    val weather: ArrayList<WeatherDetails>,
    @Embedded(prefix = "wind_") val wind: Wind
)

@Entity(tableName = "weather_wind")
data class Wind(
    @PrimaryKey(autoGenerate = false) var id_counts: Int = 1,
    val degs: Int,
    val speeds: Double
): com.a5corp.weather.data.network.response.current.Wind(id_counts, speeds, degs)

@Entity(tableName = "weather_details")
data class WeatherDetails(
    @PrimaryKey(autoGenerate = false) override var id_count: Int = 1,
    override val id: Int,
    override val main: String,
    override val description: String,
    override val icon: String
): com.a5corp.weather.data.network.response.current.WeatherDetails(id_count, id, main, description, icon)

@Entity(tableName = "weather_main")
data class Main(
    @PrimaryKey(autoGenerate = false) var id_counts: Int = 1,
    val temps: Double,
    @SerializedName("feels_like") val feelsLikes: Double,
    @SerializedName("temp_min") val tempMins: Double,
    @SerializedName("temp_max") val tempMaxs: Double,
    val humiditys: Int,
    val pressures: Int
): com.a5corp.weather.data.network.response.current.Main(id_counts, temps, feelsLikes, tempMins, tempMaxs, pressures, humiditys)

@Entity(tableName = "weather_coord")
data class Coord(
    @PrimaryKey(autoGenerate = false) var id_counts: Int = 1,
    val lats: Double,
    val lons: Double
): com.a5corp.weather.data.network.response.current.Coord(id_counts, lats, lons)

@Entity(tableName = "future_city")
data class City(
    @PrimaryKey(autoGenerate = false) var id_count: Int = 1,
    @Embedded(prefix = "coord_") val coord: Coord,
    val country: String,
    val id: Int,
    val name: String,
    val population: Int,
    val sunrise: Int,
    val sunset: Int,
    val timezone: Int
)

object FutureTypeConverters {
    val gson = Gson()

    @TypeConverter @JvmStatic fun fromDetailList(value: ArrayList<WeatherList>): String {
        return gson.toJson(value)
    }

    @TypeConverter @JvmStatic fun toDetailList(value: String): ArrayList<WeatherList> {
        val type = object : TypeToken<ArrayList<WeatherList>>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter @JvmStatic fun fromWeatherList(value: ArrayList<WeatherDetails>): String {
        return gson.toJson(value)
    }

    @TypeConverter @JvmStatic fun toWeatherList(value: String): ArrayList<WeatherDetails> {
        val type = object : TypeToken<ArrayList<WeatherList>>() {}.type
        return gson.fromJson(value, type)
    }
}