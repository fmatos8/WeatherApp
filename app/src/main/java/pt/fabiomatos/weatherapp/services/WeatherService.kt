package pt.fabiomatos.weatherapp.services

import pt.fabiomatos.weatherapp.Current
import pt.fabiomatos.weatherapp.Response
import pt.fabiomatos.weatherapp.models.Forecast
import retrofit2.http.GET

interface WeatherService {

    @GET("3.0/onecall")
    suspend fun getWeather(): Response

    @GET("2.5/forecast")
    suspend fun getWeatherDays(): Forecast
}