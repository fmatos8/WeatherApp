package pt.fabiomatos.weatherapp.utils

import okhttp3.OkHttpClient
import pt.fabiomatos.weatherapp.services.WeatherService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    val client = OkHttpClient.Builder()
        .addInterceptor(QueryParameterInterceptor())
        .build()

    val service: WeatherService by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/3.0/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherService::class.java)
    }
}