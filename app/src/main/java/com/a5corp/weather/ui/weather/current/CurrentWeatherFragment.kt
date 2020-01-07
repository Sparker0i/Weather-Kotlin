package com.a5corp.weather.ui.weather.current

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer

import com.a5corp.weather.R
import com.a5corp.weather.data.network.ConnectivityInterceptorImpl
import com.a5corp.weather.data.network.OpenWeatherMapApiService
import com.a5corp.weather.data.network.WeatherNetworkDataSource
import com.a5corp.weather.data.network.WeatherNetworkDataSourceImpl
import com.a5corp.weather.ui.base.ScopedFragment
import kotlinx.android.synthetic.main.current_weather_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
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

            updateLocation(it.cityName)
            updateDateToToday()
            updateTemperature(it.temperature, it.feelsLikeTemperature)
            updateWind(it.windDirection.toString(), it.windSpeed)
            updateVisibility(it.visibility.toDouble())

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
        textView_condition.text = condition
    }

    private fun updatePrecipitation(precipitation: Double) {
        val unitAbbreviation = chooseLocalizedUnitAbbreviation("mm", "in")
        textView_precipitation.text = "Precipitation: $precipitation $unitAbbreviation"
    }

    private fun updateWind(windDirection: String, windSpeed: Double) {
        val unitAbbreviation = chooseLocalizedUnitAbbreviation("kph", "mph")
        textView_precipitation.text = "Wind: $windDirection°, $windSpeed $unitAbbreviation"
    }

    private fun updateVisibility(visibilityDistance: Double) {
        val unitAbbreviation = chooseLocalizedUnitAbbreviation("km", "mi")
        textView_precipitation.text = "Visibility: $visibilityDistance $unitAbbreviation"
    }

    private fun changeWeatherIcon() {
        //TODO: Migrate logic of Old App's Weather Icon
    }
}
