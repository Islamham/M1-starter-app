package com.cpen321.usermanagement.data.repository

import com.cpen321.usermanagement.data.remote.dto.WeatherCurrent

import kotlin.Result;

interface WeatherRepository{
    suspend fun getWeather(location: String = ""): Result<WeatherCurrent?>
}

