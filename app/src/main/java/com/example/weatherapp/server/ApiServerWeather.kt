package com.example.weatherapp.server

import com.example.weatherapp.data.CurrentDay.CurrentDay
import com.example.weatherapp.data.WeatherCity.DayWeatherCity
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServerWeather {
    @GET("forecast")
    fun getCityWeather(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("appid") api:String): Call<DayWeatherCity>
    @GET("weather")
    fun getCurrentWeather(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("appid") api:String): Call<CurrentDay>
}
