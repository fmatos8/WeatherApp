package pt.fabiomatos.weatherapp.viewmodels

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import pt.fabiomatos.weatherapp.ListDay
import kotlinx.coroutines.launch
import pt.fabiomatos.weatherapp.Current
import pt.fabiomatos.weatherapp.Response
import pt.fabiomatos.weatherapp.repository.WeatherRepository
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class BaseViewModel : ViewModel() {

    private val repository = WeatherRepository()



    private val _current = mutableStateOf<Response?>(null)
    val current: State<Response?> get() = _current

    private val _daily = MutableLiveData<List<ListDay>>()
    val daily: LiveData<List<ListDay>> get() = _daily

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> get() = _isLoading

    internal fun fetchWeather() {
        if (_current.value != null) return // Avoid multiple calls

        _isLoading.value = true

        viewModelScope.launch {
            try {
                val response = repository.getWeather()
                _current.value = response

                val responseDays = repository.getWeatherDays()
                _daily.value = responseDays.list

                Log.i("FETCH  CURRENT -> ", _current.value!!.toString())

            } catch (e: Exception) {
                Log.e("ERRO FETCH", e.toString())
            } finally {
                _isLoading.value = false
            }
        }
    }

//    @RequiresApi(Build.VERSION_CODES.O)
//    fun GroupDaysByWeekDay(daily: List<ListDay>): Map<LocalDate, List<ListDay>> {
//        // Group the items by day
//        return groupItemsByDay(daily)
//
////        // Print the grouped items
////        for ((date, items) in groupedItems) {
////            println(date)
////            for (item in items) {
////                println("  $item")
////            }
////        }
//    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun GroupItemsByDay(items: List<ListDay>): Map<LocalDate, List<ListDay>> {
        val formatter = DateTimeFormatter.ISO_DATE_TIME
        return items.groupBy { LocalDateTime.parse(LocalDateTime.ofInstant(Instant.ofEpochSecond(it.dt!!), ZoneId.systemDefault())
            .toString(), formatter).toLocalDate() }
    }
}