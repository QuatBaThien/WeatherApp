package com.example.weatherapp.View.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.ViewModel.DayWeatherViewModel
import com.example.weatherapp.data.DayWeather
import com.example.weatherapp.data.SimplifyData
import com.example.weatherapp.databinding.RvItemWeatherHourBinding
import java.util.*

class DayWeatherAdapter (private val viewModel:DayWeatherViewModel,
                         private val date:String
) : RecyclerView.Adapter<DayWeatherAdapter.ViewHolder>(){
    var listWeatherHour = listOf<SimplifyData>()

    set(value) {
        field = value
        notifyDataSetChanged()
    }
    lateinit var mContext: Context

    class ViewHolder(var binding: RvItemWeatherHourBinding): RecyclerView.ViewHolder(binding.root) {
        fun onBind(simplifyData: SimplifyData,viewModel: DayWeatherViewModel){
            binding.apply {
                    binding.rvTvTime.text = simplifyData.hour
                    binding.rvTvDegree.text =simplifyData.temp
                    binding.rvIvWeather.setImageResource(viewModel.getImage(simplifyData))

            }
            Log.d("check", "onBind: ")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        mContext = parent.context
        val binding = RvItemWeatherHourBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }


    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(listWeatherHour[position],viewModel)
        holder.binding.rvTvTime.setTextColor(Color.GRAY)
        holder.binding.rvItemWeatherHour.background=mContext.getDrawable(R.drawable.background_weather_hour)
//        if (((viewModel.getRealTime()>=listWeatherHour[position].hour!!.substring(0..1).toInt()))
//            && (viewModel.getRealTime()<listWeatherHour[position+1].hour!!.substring(0..1).toInt())
//            &&(date!="Tomorrow")){
//            holder.binding.rvTvTime.text="now"
//            holder.binding.rvTvTime.setTextColor(Color.BLACK)
//            holder.binding.rvItemWeatherHour.background=mContext.getDrawable(R.drawable.background_weather_now)
//
//        }
    }



    override fun getItemCount(): Int {
        return listWeatherHour.size
    }
}