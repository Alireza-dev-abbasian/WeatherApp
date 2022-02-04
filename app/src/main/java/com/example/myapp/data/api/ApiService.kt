package com.example.myapp.data.api

import com.example.myapp.data.model.currentweather.CurrentWeather
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Part
import retrofit2.http.Query

interface ApiService {

    @GET("weather")
    suspend fun getCurrentWeather(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("appid") appid: String = "75110295f9d66d1e4e06e941f8f5f79e",
    ): Response<CurrentWeather>

}