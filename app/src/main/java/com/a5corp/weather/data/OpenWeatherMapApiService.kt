package com.a5corp.weather.data

import com.a5corp.weather.BuildConfig
import com.a5corp.weather.data.response.current.CurrentWeatherResponse
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherMapApiService {
    @GET("weather") fun getCurrentWeather(@Query("q") location: String): Deferred<CurrentWeatherResponse>
    @GET("weather") fun getCurrentWeather(@Query("lat") latitude: Double, @Query("lon") longitude: Double): Deferred<CurrentWeatherResponse>

    companion object {
        operator fun invoke(): OpenWeatherMapApiService {
            val requestInterceptor = Interceptor {chain ->
                val url = chain.request()
                    .url()
                    .newBuilder()
                    .addQueryParameter("appid", BuildConfig.OWM_API_KEY)
                    .build()

                val request = chain.request()
                    .newBuilder()
                    .url(url)
                    .build()

                return@Interceptor chain.proceed(request)
            }

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BuildConfig.OWM_API_URL)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(OpenWeatherMapApiService::class.java)
        }
    }
}