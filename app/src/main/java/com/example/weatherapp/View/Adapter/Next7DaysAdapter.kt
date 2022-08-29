package com.example.weatherapp.View.Adapter

import android.content.Context
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.View.Adapter.Next7DaysAdapter
import com.example.weatherapp.ViewModel.DayWeatherViewModel
import com.example.weatherapp.data.DayWeather
import com.example.weatherapp.data.Next7Days
import com.example.weatherapp.data.SimplifyData
import com.example.weatherapp.databinding.RvItemNext7daysBinding
import com.example.weatherapp.databinding.RvItemWeatherHourBinding
import java.time.LocalDate
import java.time.temporal.ChronoUnit

class Next7DaysAdapter(
    private val viewModel: DayWeatherViewModel
):  RecyclerView.Adapter<Next7DaysAdapter.ViewHolder>(
){
    var listWeather7days = listOf<SimplifyData>()
    var clickedPos : Int = -1
    lateinit var mContext: Context
    inner class ViewHolder(var binding: RvItemNext7daysBinding): RecyclerView.ViewHolder(binding.root) {
        fun onBind(next7Days: SimplifyData, position: Int,viewModel: DayWeatherViewModel) {
            itemView.run {
                binding.apply {
                    rvTvDate.text = next7Days.day
                    rvTvDegree.text = next7Days.temp+"°"
                    rvTvRain.text = next7Days.rainFall+" cm"
                    rvTvWind.text = next7Days.wind+" km/h"
                    rvTvHumidity.text = next7Days.humidity+" %"
                    rvIvWeather7days.setImageResource(viewModel.getImage(next7Days))
                    Log.d("checkk", "onBind: ${next7Days.icon} ")
                    rvItem7days.setOnClickListener {
                        listWeather7days[position].isCheckCLick=!listWeather7days[position].isCheckCLick
                        rv7daysClicked.isVisible=listWeather7days[position].isCheckCLick

                        listWeather7days.forEach {
                            if (it != next7Days && it.isCheckCLick){
                                it.isCheckCLick = false
                                notifyDataSetChanged()
                            }
                        }
                    }
                    if (next7Days.isCheckCLick){
                        rv7daysClicked.visibility = View.VISIBLE
                    }else{
                        rv7daysClicked.visibility = View.GONE
                    }
                }
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        mContext = parent.context
        val binding = RvItemNext7daysBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d("checkk", "onBindViewHolder: ")
        var nextday = LocalDate.now().plus(1, ChronoUnit.DAYS).dayOfWeek
        listWeather7days[0].day="Tomorrow"
        for (i in 1 until listWeather7days.size){
            listWeather7days[i].day=nextday.toString()
            nextday = LocalDate.now().plus((1+i).toLong(), ChronoUnit.DAYS).dayOfWeek
        }
        holder.onBind(listWeather7days[position],position,viewModel)
    }


    override fun getItemCount(): Int {
        return listWeather7days.size
    }

}