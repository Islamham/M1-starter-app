package com.cpen321.usermanagement.api

//import com.cpen321.usermanagement.BuildConfig

class WeatherService {
    private val baseUrl = "http://api.weatherstack.com/forecast"
//    private val accessKey = BuildConfig.WEATHER_API_KEY
//
//    fun getForecast(
//        query: String = "New York",
//        forecastDays: Int = 1,
//        hourly: Int = 1
//    ): String? {
//        val url = "$baseUrl?access_key=$accessKey&query=$query&forecast_days=$forecastDays&hourly=$hourly"
//        val request = Request.Builder()
//            .url(url)
//            .get()
//            .build()
//        client.newCall(request).execute().use { response ->
//            return if (response.isSuccessful) response.body?.string() else null
//        }
//    }
}
