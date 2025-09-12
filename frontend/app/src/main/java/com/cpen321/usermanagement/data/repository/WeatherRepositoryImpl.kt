package com.cpen321.usermanagement.data.repository

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepositoryImpl @Inject constructor(
) : WeatherRepository {

    companion object {
        private const val TAG = "WeatherRepositoryImpl"
    }

    override suspend fun getWeather(): Result<String> {
        TODO("Not yet implemented")
    }


}
