package com.example.weatherapp

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.View.Adapter.Next7DaysAdapter
import com.example.weatherapp.ViewModel.DayWeatherViewModel
import com.example.weatherapp.data.Next7Days
import com.example.weatherapp.data.SimplifyData
import com.example.weatherapp.databinding.ActivityMainBinding
import com.example.weatherapp.databinding.FragmentNext7DaysBinding
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import kotlin.concurrent.fixedRateTimer
import kotlin.math.log

class Next7DaysFragment : Fragment() {
    private lateinit var binding: FragmentNext7DaysBinding
    private lateinit var binding1: ActivityMainBinding
    lateinit var next7DaysAdapter: Next7DaysAdapter
    private val viewModel: DayWeatherViewModel by lazy {
        ViewModelProvider(requireActivity()).get(DayWeatherViewModel::class.java)
    }
    companion object {

        @JvmStatic
        fun newInstance() =
            Next7DaysFragment()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        binding.rv7days.scrollToPosition(0)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentNext7DaysBinding.inflate(inflater,container,false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        next7DaysAdapter= Next7DaysAdapter(viewModel)
        initRecycleView()
        initData()
        binding.icBack.setOnClickListener {
            fragmentManager?.popBackStack()

        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    @OptIn(InternalCoroutinesApi::class)
    private fun initData() {
        var list = arrayListOf<SimplifyData>()
        var j:Int=viewModel.count
        var i:Int=0

        //val formattedTomorrow = tomorrow.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        viewModel._dataList.onEach {
            if (it.isNotEmpty()){
                while (i<=4){
                    var d1:SimplifyData=SimplifyData()
                    d1.day=it[j+i*8].day
                    d1.hour=it[j+i*8].hour
                    d1.temp=it[j+i*8].temp
                    d1.humidity=it[j+i*8].humidity
                    d1.rainFall=it[j+i*8].rainFall
                    d1.wind=it[j+i*8].wind
                    d1.icon=it[j+i*8].icon
                    d1.description=it[j+i*8].description
                    list.add(d1)
                    i++
                }
            }
            next7DaysAdapter.listWeather7days=list
        }.launchIn(requireActivity().lifecycleScope)

    }
    private fun initRecycleView() {
        binding.rv7days.apply {
            adapter = next7DaysAdapter
            layoutManager = LinearLayoutManager(context)

        }
    }


}


