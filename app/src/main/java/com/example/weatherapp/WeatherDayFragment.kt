package com.example.weatherapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.View.Adapter.DayWeatherAdapter
import com.example.weatherapp.ViewModel.DayWeatherViewModel
import com.example.weatherapp.data.DayWeather
import com.example.weatherapp.data.SimplifyData
import com.example.weatherapp.databinding.FragmentWeatherDayBinding
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.*


class WeatherDayFragment(date:String) : Fragment() {
    private lateinit var binding: FragmentWeatherDayBinding
    lateinit var dayWeatherAdapter : DayWeatherAdapter
    var date=date
    private val viewModel: DayWeatherViewModel by lazy {
        ViewModelProvider(requireActivity()).get(DayWeatherViewModel::class.java)
    }
    companion object {
        @JvmStatic
        fun newInstance(date: String) = WeatherDayFragment(date)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentWeatherDayBinding.inflate(inflater,container,false)
        return binding.root

    }

    override fun onResume() {
        super.onResume()
        initData()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        dayWeatherAdapter = DayWeatherAdapter(viewModel,date)
        binding.rv.adapter = dayWeatherAdapter
        binding.rv.layoutManager= LinearLayoutManager(context)
        binding.rv.setLayoutManager(
            LinearLayoutManager(
                activity,
                LinearLayoutManager.HORIZONTAL,
                false
            )
        )



    }

    private fun initData() {
        var todayCount:Int=1
        var tomorrowCount:Int=1
        var list = arrayListOf<SimplifyData>()
        var list1 = arrayListOf<SimplifyData>()
        var temp:Int=0
        var i:Int=0
        var j:Int=1
        viewModel._dataList.onEach {
          if (it.isNotEmpty()){
              while (it[i].day==it[0].day){
                  list.add(it[i])
                  i++
              }
              temp=i
              j=i
              while (it[temp].day==it[j].day) {
                  list1.add(it[j])
                  j++
              }
              viewModel.count=i
              dayWeatherAdapter.listWeatherHour =  if (date=="Today") list else list1
          }
        }.launchIn(requireActivity().lifecycleScope)

    }

}
