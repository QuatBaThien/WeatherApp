package com.example.weatherapp.data

import android.media.Image

data class Next7Days(
    var date: String,
    var degree: String,
    var image: Int,
    var rainFall: String,
    var wind: String,
    var humidity: String,
    var checkClicked: Boolean = false
)
