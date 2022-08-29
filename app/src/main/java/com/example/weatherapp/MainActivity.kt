package com.example.weatherapp

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ContextThemeWrapper
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.ViewModel.DayWeatherViewModel
import com.example.weatherapp.databinding.ActivityMainBinding
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    var lat: String=""
    var lon:String= ""
    val api: String = "06c921750b9a82d8f5d1294e1586276f"
    private var locationRequest: LocationRequest? = null
    private lateinit var mFusedLocationClient: FusedLocationProviderClient
    private val viewModel: DayWeatherViewModel by lazy {
        ViewModelProvider(this)[DayWeatherViewModel::class.java]
    }
    lateinit var binding: ActivityMainBinding
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onResume() {
        super.onResume()
        //viewModel.getLocationCity(binding)
       // getCurrentLocation()
    }
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel.mContext=this
        supportFragmentManager.beginTransaction().add(R.id.layout,WeatherDayFragment("Today")).commit()
        binding.tabBarLayout.click1.isVisible = true
        binding.tabBarLayout.commerceFirstText.setTextAppearance(this,R.style.tab_bar_text_clicked)
        binding.titleFragment.tvDate.text=getDateReal()
        binding.evCitySearch.isClickable =false
        //get location.
        locationRequest = LocationRequest.create()
        locationRequest!!.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
        locationRequest!!.setInterval(5000)
        locationRequest!!.setFastestInterval(2000)
        mFusedLocationClient=LocationServices.getFusedLocationProviderClient(this)
        getCurrentLocation()
        //reload
        binding.noInternetButton.setOnClickListener {
              binding.noInternetButton.visibility=View.GONE
              binding.loading.visibility=View.VISIBLE
              if (viewModel.city!=""){
                  viewModel.getLocationCity(binding)
                  Log.d("checkk", "onCreate: ${viewModel.city}")
              }else
                  getCurrentLocation()
//            val i = Intent(this@MainActivity, MainActivity::class.java)
//            finish()
//            overridePendingTransition(0, 0)
//            startActivity(i)
//            overridePendingTransition(0, 0)
        }
        //---searched location
        binding.searchedLocation.setOnClickListener {
        }


        binding.evCitySearch.setOnQueryTextListener(object: SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.city = query.toString()
                Log.e("city", viewModel.city)
//                viewModel.getCurrentWeatherCtiy(binding)
                viewModel.getLocationCity(binding)
                binding.evCitySearch.clearFocus()
                binding.loading.visibility=View.VISIBLE
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }
        })
    }
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (isGPSEnabled()) {
                    getCurrentLocation()
                } else {
                    turnOnGPS()
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onActivityResult(requestCode: Int, resultCode: Int, @Nullable data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                getCurrentLocation()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun getCurrentLocation() {
            if (ActivityCompat.checkSelfPermission(
                    this@MainActivity,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                if (isGPSEnabled()) {
                    binding.loading.visibility=View.VISIBLE
                    LocationServices.getFusedLocationProviderClient(this@MainActivity)
                        .requestLocationUpdates(locationRequest!!, object : LocationCallback() {
                            override fun onLocationResult(locationResult: LocationResult) {
                                super.onLocationResult(locationResult)
                                LocationServices.getFusedLocationProviderClient(this@MainActivity)
                                    .removeLocationUpdates(this)
                                if (locationResult != null && locationResult.locations.size > 0) {
                                    val index = locationResult.locations.size - 1
                                    viewModel.lat = locationResult.locations[index].latitude.toString()
                                    viewModel.lon = locationResult.locations[index].longitude.toString()
                                    Log.d("checkk", "\"Latitude: ${viewModel.lat}\" ")
                                    viewModel.getWeatherCity(binding)
                                }
                            }
                        }, Looper.getMainLooper())
                } else {
                    turnOnGPS()
                }
            } else {
                requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
            }
    }

    private fun turnOnGPS() {
        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest!!)
        builder.setAlwaysShow(true)
        val result: Task<LocationSettingsResponse> = LocationServices.getSettingsClient(
            applicationContext
        )
            .checkLocationSettings(builder.build())
        result.addOnCompleteListener(OnCompleteListener<LocationSettingsResponse?> { task ->
            try {
                val response = task.getResult(ApiException::class.java)
                Toast.makeText(this@MainActivity, "GPS is already tured on", Toast.LENGTH_SHORT)
                    .show()
            } catch (e: ApiException) {
                when (e.statusCode) {
                    LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> try {
                        val resolvableApiException = e as ResolvableApiException
                        resolvableApiException.startResolutionForResult(this@MainActivity, 2)
                    } catch (ex: IntentSender.SendIntentException) {
                        ex.printStackTrace()
                    }
                    LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {}
                }
            }
        })
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun isGPSEnabled(): Boolean {
        var locationManager: LocationManager? = null
        var isEnabled = false
        if (locationManager == null) {
            locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        }
        isEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        return isEnabled
    }

    fun getDateReal() : String{
        val df: DateFormat = SimpleDateFormat("EEE, MMM d", Locale.ENGLISH)
        val date: String = df.format(Calendar.getInstance().time)
        return date
    }


    @SuppressLint("ResourceType")
    fun onClick (v: View){
        when (v.id){
            R.id.Today -> {
                supportFragmentManager.beginTransaction().replace(
                R.id.layout,
                WeatherDayFragment("Today")
            ).commit()
                binding.tabBarLayout.click1.isVisible = true
                binding.tabBarLayout.click2.isVisible = false
                binding.tabBarLayout.commerceFirstText.setTextAppearance(this,R.style.tab_bar_text_clicked)
                binding.tabBarLayout.commerceSecText.setTextAppearance(this,R.style.tab_bar_text)
            }
            R.id.Tomorrow ->{

                supportFragmentManager.beginTransaction().replace(
                R.id.layout,
                WeatherDayFragment("Tomorrow")
            ).commit()
                binding.tabBarLayout.click1.isVisible = false
                binding.tabBarLayout.click2.isVisible = true
                binding.tabBarLayout.commerceFirstText.setTextAppearance(this,R.style.tab_bar_text)
                binding.tabBarLayout.commerceSecText.setTextAppearance(this,R.style.tab_bar_text_clicked)
            }
            R.id.next7days ->{supportFragmentManager.beginTransaction().apply {
                replace(R.id.layout_7days,Next7DaysFragment.newInstance())
                addToBackStack("Next7DaysFragment")
                commit()
            }
                binding.evCitySearch.isClickable
            }
        }
    }
}