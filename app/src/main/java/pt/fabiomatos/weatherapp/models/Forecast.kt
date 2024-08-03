package pt.fabiomatos.weatherapp.models

import pt.fabiomatos.weatherapp.ListDay
import com.google.gson.annotations.SerializedName
import pt.fabiomatos.weatherapp.Current
import pt.fabiomatos.weatherapp.Daily
import pt.fabiomatos.weatherapp.Hourly
import pt.fabiomatos.weatherapp.Minutely

data class Forecast(
    @SerializedName("cod")
    var cod: Double? = null,
    @SerializedName("message")
    var message: Int? = null,
    @SerializedName("cnt")
    var cnt: Int? = null,
    @SerializedName("list")
    var list: ArrayList<ListDay> = arrayListOf()
)
