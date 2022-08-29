package com.example.weatherapp.server
import com.example.weatherapp.data.Location.LocationCity
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServerLocation {
    @GET("direct")
   fun getCityLocation(
        @Query("q") city :String,
        @Query ("appid") id: String
    ):Call<LocationCity>
}