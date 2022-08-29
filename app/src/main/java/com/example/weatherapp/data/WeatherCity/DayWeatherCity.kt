package com.example.weatherapp.data.WeatherCity

import com.google.gson.annotations.SerializedName

data class DayWeatherCity(
    @SerializedName("cod"     ) var cod     : String?         = null,
    @SerializedName("message" ) var message : Int?            = null,
    @SerializedName("cnt"     ) var cnt     : Int?            = null,
    @SerializedName("list"    ) var list    : ArrayList<WeatherItem> = arrayListOf(),
    @SerializedName("city"    ) var city    : City?           = City()
)