package com.example.weatherapp.data

import com.example.weatherapp.data.WeatherCity.DayWeatherCity
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.ArrayList

data class SearchedData(
    var city: String?=null,
    var savedDataList: MutableStateFlow<ArrayList<SimplifyData>> = MutableStateFlow(arrayListOf())
)
