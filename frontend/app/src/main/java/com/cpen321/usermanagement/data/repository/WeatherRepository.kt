package com.cpen321.usermanagement.data.repository

import com.cpen321.usermanagement.data.remote.dto.User;

import kotlin.Result;

interface WeatherRepository {
    suspend fun getWeather(): Result<String>
}

