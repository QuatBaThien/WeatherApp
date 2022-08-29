package com.example.weatherapp.data

data class SimplifyData(
    var temp: String?=null,
    var day: String?=null,
    var hour: String?=null,
    var rainFall:String?=null,
    var wind:String?=null,
    var humidity:String?=null,
    var description: String?=null,
    var icon: String?=null,
    var city:String?=null,
    var isCheckCLick:Boolean=false
)
