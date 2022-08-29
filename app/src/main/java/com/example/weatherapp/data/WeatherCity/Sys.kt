package com.example.weatherapp.data.WeatherCity

import com.google.gson.annotations.SerializedName

data class Sys(
    @SerializedName("pod" ) var pod : String? = null,
    @SerializedName("type"    ) var type    : Int?    = null,
    @SerializedName("id"      ) var id      : Int?    = null,
    @SerializedName("country" ) var country : String? = null,
    @SerializedName("sunrise" ) var sunrise : Int?    = null,
    @SerializedName("sunset"  ) var sunset  : Int?    = null
)