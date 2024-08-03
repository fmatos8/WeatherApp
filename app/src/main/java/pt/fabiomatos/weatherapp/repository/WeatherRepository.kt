package pt.fabiomatos.weatherapp.repository

import pt.fabiomatos.weatherapp.Current
import pt.fabiomatos.weatherapp.Response
import pt.fabiomatos.weatherapp.utils.RetrofitInstance

class WeatherRepository {
    private val api = RetrofitInstance.service

    suspend fun getWeather(): Response {
        return api.getWeather()
    }
}