package com.cpen321.usermanagement.data.remote.dto

data class WeatherRequest(
    val type: String,
    val query: String,
    val language: String,
    val unit: String
)

data class WeatherLocation(
    val name: String,
    val country: String,
    val region: String,
    val lat: String,
    val lon: String,
    val timezone_id: String,
    val localtime: String,
    val localtime_epoch: Long,
    val utc_offset: String
)

data class WeatherCurrent(
    val observation_time: String,
    val temperature: Int,
    val weather_code: Int,
    val weather_icons: List<String>,
    val weather_descriptions: List<String>,
    val astro: WeatherAstro,
    val air_quality: WeatherAirQuality,
    val wind_speed: Int,
    val wind_degree: Int,
    val wind_dir: String,
    val pressure: Int,
    val precip: Double,
    val humidity: Int,
    val cloudcover: Int,
    val feelslike: Int,
    val uv_index: Int,
    val visibility: Int,
    val is_day: String
)

data class WeatherAstro(
    val sunrise: String,
    val sunset: String,
    val moonrise: String,
    val moonset: String,
    val moon_phase: String,
    val moon_illumination: Int
)

data class WeatherAirQuality(
    val co: String,
    val no2: String,
    val o3: String,
    val so2: String,
    val pm2_5: String,
    val pm10: String,
    val `us-epa-index`: String,
    val `gb-defra-index`: String
)