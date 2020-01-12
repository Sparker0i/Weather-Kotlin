package com.a5corp.weather

import android.app.Application
import android.content.Context
import androidx.preference.PreferenceManager
import com.a5corp.weather.data.db.ForecastDatabase
import com.a5corp.weather.data.network.*
import com.a5corp.weather.data.provider.*
import com.a5corp.weather.data.repository.ForecastRepository
import com.a5corp.weather.data.repository.ForecastRepositoryImpl
import com.a5corp.weather.ui.settings.ThemeHelper
import com.a5corp.weather.ui.weather.current.CurrentWeatherViewModelFactory
import com.google.android.gms.location.LocationServices
import com.jakewharton.threetenabp.AndroidThreeTen
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class ForecastApplication: Application(), KodeinAware {
    override val kodein: Kodein = Kodein.lazy {
        import(androidXModule(this@ForecastApplication))

        bind() from singleton {
            ForecastDatabase(
                instance()
            )
        }
        bind() from singleton { instance<ForecastDatabase>().currentWeatherDao() }
        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }
        bind() from singleton { OpenWeatherMapApiService(instance()) }
        bind() from singleton { PreferenceProvider(instance()) }
        bind<WeatherNetworkDataSource>() with singleton { WeatherNetworkDataSourceImpl(instance()) }
        bind<ForecastRepository>() with singleton { ForecastRepositoryImpl(instance(), instance(), instance(), instance()) }
        bind() from provider { LocationServices.getFusedLocationProviderClient(instance<Context>()) }
        bind<LocationProvider>() with singleton { LocationProviderImpl(instance(), instance()) }
        bind<UnitProvider>() with singleton { UnitProviderImpl(instance()) }
        bind() from provider { CurrentWeatherViewModelFactory(instance(), instance()) }
    }

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false)
        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        val themePref = prefs.getString(getString(R.string.theme_pref_key), ThemeHelper.LIGHT_MODE)
        ThemeHelper.applyTheme(themePref!!)
    }
}