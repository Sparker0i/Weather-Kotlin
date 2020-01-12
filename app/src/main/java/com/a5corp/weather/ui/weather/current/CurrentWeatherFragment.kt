package com.a5corp.weather.ui.weather.current

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.a5corp.weather.R
import com.a5corp.weather.ui.base.ScopedFragment
import kotlinx.android.synthetic.main.current_weather_fragment.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance


class CurrentWeatherFragment : ScopedFragment(), KodeinAware {

    override val kodein by closestKodein()

    private val viewModelFactory: CurrentWeatherViewModelFactory by instance()
    private lateinit var viewModel: CurrentWeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.current_weather_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(CurrentWeatherViewModel::class.java)
            //ViewModelProviders.of(this).get(CurrentWeatherViewModel::class.java)

        bindUi()
    }

    private fun bindUi() = launch {
        val currentWeather = viewModel.weather.await()
        group_loading.visibility = View.GONE
        currentWeather.observe(this@CurrentWeatherFragment, Observer {
            if (it == null) return@Observer

            updateLocation(it.name)
            updateDateToToday()
            updateTemperature(it.main.temp, it.main.feelsLike)
            updateWind(it.wind.deg.toString(), it.wind.speed)
            updateVisibility(it.visibility.toDouble())
            updateCondition(it.weather!![0].description)

            changeWeatherIcon()
        })
    }

    private fun chooseLocalizedUnitAbbreviation(metric: String, imperial: String): String {
        return if (viewModel.isMetric) metric else imperial
    }

    private fun updateLocation(location: String) {
        (activity as AppCompatActivity).supportActionBar?.title = location
    }

    private fun updateDateToToday() {
        (activity as AppCompatActivity).supportActionBar?.subtitle = "Today"
    }

    private fun updateTemperature(temperature: Double, temperatureFeelsLike: Double) {
        val unitAbbreviation = chooseLocalizedUnitAbbreviation("°C", "°F")
        textView_temperature.text = "$temperature$unitAbbreviation"
        textView_feels_like_temperature.text = "Feels Like: $temperatureFeelsLike$unitAbbreviation"
    }

    private fun updateCondition(condition: String) {
        val strArray = condition.split(" ")
        val builder = StringBuilder()
        for (s in strArray) {
            val cap = s.substring(0, 1).toUpperCase() + s.substring(1)
            builder.append("$cap ")
        }
        textView_condition.text = builder.toString()
    }

    private fun updatePrecipitation(precipitation: Double) {
        val unitAbbreviation = chooseLocalizedUnitAbbreviation("mm", "in")
        textView_precipitation.text = "Precipitation: $precipitation $unitAbbreviation"
    }

    private fun updateWind(windDirection: String, windSpeed: Double) {
        val unitAbbreviation = chooseLocalizedUnitAbbreviation("kph", "mph")
        textView_wind.text = "Wind: $windDirection°, $windSpeed $unitAbbreviation"
    }

    private fun updateVisibility(visibilityDistance: Double) {
        val unitAbbreviation = chooseLocalizedUnitAbbreviation("m", "mi")
        textView_visibility.text = "Visibility: $visibilityDistance $unitAbbreviation"
    }

    private fun changeWeatherIcon() {
        //TODO: Migrate logic of Old App's Weather Icon
    }
}
