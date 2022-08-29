package com.example.weatherapp.data.WeatherCity

import com.google.gson.annotations.SerializedName

data class Rain(
    @SerializedName("3h" ) var hour3 : Double? = null
)