package com.cpen321.usermanagement.data.remote.api

import com.cpen321.usermanagement.data.remote.dto.WeatherApiResponse
import retrofit2.http.GET
import retrofit2.http.Query
interface WeatherInterface {

    @GET("current")
    suspend fun getWeather(
        @Query("access_key") accessKey: String,
        @Query("query") query: String
    ): retrofit2.Response<WeatherApiResponse>
}
