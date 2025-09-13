package com.cpen321.usermanagement.data.repository

import android.util.Log
import com.cpen321.usermanagement.BuildConfig
import javax.inject.Inject
import javax.inject.Singleton

import com.cpen321.usermanagement.data.remote.api.WeatherInterface
import com.cpen321.usermanagement.data.remote.dto.WeatherCurrent

@Singleton
class WeatherRepositoryImpl @Inject constructor(
    private val weatherInterface: WeatherInterface,
) : WeatherRepository {

    companion object {
        private const val TAG = "WeatherRepositoryImpl"
    }

    val accessKey = BuildConfig.WEATHER_API_KEY

    override suspend fun getWeather(location: String): Result<WeatherCurrent?> {
        try {
            val response = weatherInterface.getWeather(accessKey = accessKey, query = location)
            if (response.isSuccessful) {
                Log.d(TAG, "getWeather: ${response.body()?.current}")
                return Result.success(response.body()?.current)
            } else {
                val errorBodyString = response.errorBody()?.string()
                val errorMessage = errorBodyString ?: "Failed to get weather."
                Log.e(TAG, "Weather fetch failed: $errorMessage")
                return Result.failure(Exception(errorMessage))
            }
        } catch (e: java.net.SocketTimeoutException) {
            Log.e(TAG, "Network timeout during weather fetch", e)
            return Result.failure(e)
        } catch (e: java.net.UnknownHostException) {
            Log.e(TAG, "Network connection failed during weather fetch", e)
            return Result.failure(e)
        } catch (e: java.io.IOException) {
            Log.e(TAG, "IO error during weather fetch", e)
            return Result.failure(e)
        } catch (e: retrofit2.HttpException) {
            Log.e(TAG, "HTTP error during weather fetch: ${e.code()}", e)
            return Result.failure(e)
        }
    }

}


