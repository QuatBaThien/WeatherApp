package com.example.weatherapp.data.WeatherCity

import com.google.gson.annotations.SerializedName

data class Clouds(
    @SerializedName("all" ) var all : Int? = null
)