package com.example.weatherapp.ViewModel
import android.annotation.SuppressLint
import android.app.Application
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.view.ContextThemeWrapper
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModel
import com.example.weatherapp.MainActivity
import com.example.weatherapp.R
import com.example.weatherapp.data.CurrentDay.CurrentDay
import com.example.weatherapp.data.Location.LocationCity
import com.example.weatherapp.data.SearchedData
import com.example.weatherapp.data.SimplifyData
import com.example.weatherapp.data.WeatherCity.DayWeatherCity
import com.example.weatherapp.databinding.ActivityMainBinding
import com.example.weatherapp.server.ApiClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.roundToInt


class DayWeatherViewModel(): ViewModel() {
    var lat: String = "-1"
    var lon: String = "-1"
    var count:Int=1
    var city: String = ""
    lateinit var mContext:Context
    val api: String = "06c921750b9a82d8f5d1294e1586276f"
    val _dataList: MutableStateFlow<ArrayList<SimplifyData>> = MutableStateFlow(arrayListOf())
    val savedDataList: MutableStateFlow<ArrayList<SimplifyData>> = MutableStateFlow(arrayListOf())
    var searchedData: ArrayList<SearchedData> = ArrayList()
    val dataList: StateFlow<ArrayList<SimplifyData>> = _dataList
    fun getLocationCity( binding: ActivityMainBinding){
        ApiClient.getInstanceApiLocation().getCityLocation(city, api).enqueue(object :
            Callback<LocationCity> {
            @SuppressLint("NewApi")
            override fun onResponse(call: Call<LocationCity>, response: Response<LocationCity>) {

                if (response.isSuccessful) {
                    if (response.body()!!.size!=0) {
                        val citylocation: LocationCity = response.body()!!
                        lat = citylocation[0].lat.toString()
                        lon = citylocation[0].lon.toString()
                        Log.e("okok", lon)
                        getWeatherCity(binding)
                    } else {
                        Toast.makeText(mContext, "Cannot find your location", Toast.LENGTH_SHORT).show()
                        binding.loading.visibility=View.GONE
                    }
                }
            }

            override fun onFailure(call: Call<LocationCity>, t: Throwable) {
                hireView(binding)
                binding.loading.visibility=View.GONE
            }
        })
    }
//    fun getCurrentWeatherCtiy(binding: ActivityMainBinding){
//        ApiClient.getInstanceApiWeather().getCurrentWeather(lat, lon, api).enqueue(object :Callback<CurrentDay>{
//            override fun onResponse(call: Call<CurrentDay>, response: Response<CurrentDay>){
//                if (response.isSuccessful){
//                    var currentDay:CurrentDay = response.body()!!
//                    binding.apply {
//                        rowFragment.description.text=currentDay.weather[0].description
//                        titleFragment.tvCity.text=currentDay.name
//                        var temp: Double? = currentDay.main!!.temp!!.minus(273.15)
//                        rowFragment.degree.text= (+ Math.round(temp!!*100.0)/100.0).toString()
//                        weatherInfoFragment.wdTvWind.text ="${currentDay.wind!!.speed}km/h"
//                        weatherInfoFragment.wdTvHumidity.text ="${currentDay.main!!.humidity}%"
//                        binding.tabBarLayout.Today.performClick()
//                        binding.rowFragment.imageView.setImageResource(when(currentDay.weather[0].icon){
//                            "01d" ->  R.drawable.ic_sun
//                            "02d" ->  R.drawable.ic_sun_and_cloud
//                            "03d" ->  R.drawable.ic_cloud
//                            "04d" ->  R.drawable.ic_rain
//                            else -> R.drawable.ic_clound_rain_sun
//                        })
//                    }
//
//                }
//            }
//
//            override fun onFailure(call: Call<CurrentDay>, t: Throwable) {
//                hireView(binding)
//                binding.loading.visibility=View.GONE
//
//            }
//        })
//    }
    fun getWeatherCity(binding: ActivityMainBinding) {
        ApiClient.getInstanceApiWeather().getCityWeather(lat,lon, api).enqueue(object :
            Callback<DayWeatherCity> {

            @SuppressLint("NewApi")
            override fun onResponse(call: Call<DayWeatherCity>, response: Response<DayWeatherCity>) {
                var temp:String
                var day:String
                var hour:String
                var rainFall:String
                var wind:String
                var humidity:String
                var description:String
                var icon: String
                var city1:String
                var tempData:SimplifyData
                if (response.isSuccessful) {
                    innitView(binding)
                    //init data//
                    var weatherList: DayWeatherCity = response.body()!!
                    _dataList.value.clear()
                    var list: ArrayList<SimplifyData> = ArrayList()
                    for (i in 0 until weatherList.list.size){
                         var _temp: Double? = weatherList.list[i].main!!.temp!!.minus(273.15)
                         temp=_temp!!.roundToInt().toString()
                         day=weatherList.list[i].dtTxt.toString().substring(0..10)
                         hour=weatherList.list[i].dtTxt.toString().substring(11..weatherList.list[i].dtTxt.toString().length-4)
                         rainFall=weatherList.list[i].rain!!.hour3.toString()
                         if (rainFall=="null"){rainFall="0"}
                         wind=weatherList.list[i].wind!!.speed.toString()
                         humidity=weatherList.list[i].main!!.humidity.toString()
                         description= weatherList.list[i].weather[0].description!!
                         icon=weatherList.list[i].weather[0].icon!!
                         city1=weatherList.city!!.name!!
                         tempData= SimplifyData(temp, day, hour, rainFall, wind, humidity,description, icon,city1)
                         list.add(tempData)
                    }
                    _dataList.value =list
                    savedDataList.value=list
                    searchedData.add(SearchedData(city, savedDataList))

                    //init view//
                    init(list,binding)
                }

            }

            override fun onFailure(call: Call<DayWeatherCity>, t: Throwable) {
                    hireView(binding)
                    binding.loading.visibility=View.GONE
                Log.d("checkkk", "onFailure: ")


            }
        })
    }

    private fun init(list: ArrayList<SimplifyData>,binding: ActivityMainBinding) {
        for (i in 1 until list.size-1){
            if (((getRealTime()>=list[i].hour!!.substring(0..1).toInt()))
                && (getRealTime()<list[i+1].hour!!.substring(0..1).toInt()))
            {
                binding.apply {
                    rowFragment.description.text=list[i].description
                    titleFragment.tvCity.text=list[i].city
                    rowFragment.degree.text= list[i].temp
                    weatherInfoFragment.wdTvWind.text ="${list[i].wind}km/h"
                    weatherInfoFragment.wdTvHumidity.text ="${list[i].humidity}%"
                    binding.tabBarLayout.Today.performClick()
                    binding.rowFragment.imageView.setImageResource(getImage(list[i]))
                }
                break
            }
        }
    }

    fun getRealTime(): Int{
        val c= Calendar.getInstance()
        val hour=c.get(Calendar.HOUR_OF_DAY)
        return hour
    }
    fun getImage(list: SimplifyData):Int{
        return when(list.icon){
            "01d" -> R.drawable.ic_sun
            "02d" -> R.drawable.ic_sun_and_cloud
            "03d" -> R.drawable.ic_cloud
            "04d" -> R.drawable.ic_rain
            else -> R.drawable.ic_clound_rain_sun
        }

    }
    fun hireView(binding: ActivityMainBinding){
        binding.apply {
            error.isVisible=true
            noInternetButton.isVisible=true
            noInternet.isVisible=true
            noInternet.text="Some things went wrong, please check your internet connection!"
        }
    }
    fun innitView(binding: ActivityMainBinding){
        binding.apply {
            error.isVisible=false
            binding.loading.visibility= View.GONE
            binding.noInternetButton.isVisible=false
            binding.noInternet.isVisible=false
        }
    }
}


