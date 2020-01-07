package com.a5corp.weather.ui.weather.current

import androidx.lifecycle.ViewModel
import com.a5corp.weather.data.repository.ForecastRepository
import com.a5corp.weather.internal.UnitSystem
import com.a5corp.weather.internal.lazyDeferred

class CurrentWeatherViewModel(
    private val forecastRepository: ForecastRepository
) : ViewModel() {
    private val unitSystem = UnitSystem.METRIC

    private val isMetric: Boolean
        get() = unitSystem == UnitSystem.METRIC

    val weather by lazyDeferred {
        forecastRepository.getCurrentWeather(isMetric)
    }
}
