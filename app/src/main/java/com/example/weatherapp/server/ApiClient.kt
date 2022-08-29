package com.example.weatherapp.server

import android.util.Log
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {
    companion object{
        val baseUrl: String ="http://api.openweathermap.org/geo/1.0/"
        @JvmStatic

        fun getInstanceApiLocation(): ApiServerLocation {
            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

            val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
            val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(ApiServerLocation::class.java)
        }
        fun getInstanceApiWeather():ApiServerWeather{
            val httpClient: OkHttpClient.Builder = OkHttpClient.Builder()

//            httpClient.addInterceptor(object :Interceptor{
//                override fun intercept(chain: Interceptor.Chain): Response {
//                    val request = chain.request()
//                    val response = chain.proceed(request)
//                    Log.d("huhu", "intercept: ${response.body()}")
//                    return response
//                }
//            })
            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

            val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
            val baseUrl: String= "http://api.openweathermap.org/data/2.5/"
            val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create()).client(httpClient.build())
                .build()
            return retrofit.create(ApiServerWeather::class.java)
        }
    }
}
