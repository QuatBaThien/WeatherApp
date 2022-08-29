package com.example.weatherapp.Dialog

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.MainActivity
import com.example.weatherapp.R
import com.example.weatherapp.ViewModel.DayWeatherViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomDiglogSearched(mainActivity: MainActivity): BottomSheetDialogFragment() {
    private val viewModel: DayWeatherViewModel by lazy {
        ViewModelProvider(requireActivity()).get(DayWeatherViewModel::class.java)
    }
    private var list: ArrayList<ModelCityItem> = arrayListOf()
    fun receiveData(listData: List<ModelCityItem>) {
        list.clear()
        list.addAll(listData)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheet: BottomSheetDialog =
            super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        val view = LayoutInflater.from(context).inflate(R.layout.item_searched_dialog, null)
        bottomSheet.setContentView(view)
        val adapter = AdapterQuery(object : IOnclickCallWeatherOfCity {
            override fun getData(modelCityItem: ModelCityItem) {
                viewModel.apply {
                    DoSharedPreference.putString(ConstantApp.KEY_QUERY,modelCityItem.name)
                    getDataCity(modelCityItem.name, 1, ConstantApp.API_KEY)
                    getDataTodayWeather(modelCityItem.lat, modelCityItem.lon, ConstantApp.API_KEY)
                    getDataForeCaseWeather(modelCityItem.lat, modelCityItem.lon, ConstantApp.API_KEY)
                }
                bottomSheet.dismiss()
            }
        })
        // init recyclerView
        val rc = view.findViewById<RecyclerView>(R.id.rv_searched_data)
        rc.adapter = adapter
        rc.layoutManager = LinearLayoutManager(context)
        val decoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        rc.addItemDecoration(decoration)
        adapter.receiveData(list)
        // setEvent
        val close = view.findViewById<ImageView>(R.id.iv_close_bts)
        close.setOnClickListener {
            bottomSheet.dismiss()
        }
        return bottomSheet

    }
}