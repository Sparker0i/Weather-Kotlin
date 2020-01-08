package com.a5corp.weather.data.provider

import com.a5corp.weather.internal.UnitSystem

interface UnitProvider {
    fun getUnitSystem(): UnitSystem
}