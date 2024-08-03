package pt.fabiomatos.weatherapp.services

import pt.fabiomatos.weatherapp.Current
import pt.fabiomatos.weatherapp.Response
import retrofit2.http.GET

interface WeatherService {

    @GET("onecall")
    suspend fun getWeather(): Response
}