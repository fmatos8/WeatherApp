package pt.fabiomatos.weatherapp.viewmodels

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pt.fabiomatos.weatherapp.Current
import pt.fabiomatos.weatherapp.Response
import pt.fabiomatos.weatherapp.repository.WeatherRepository

class BaseViewModel : ViewModel() {

    private val repository = WeatherRepository()



    private val _current = mutableStateOf<Response?>(null)
    val current: State<Response?> get() = _current



    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> get() = _isLoading

    internal fun fetchWeather() {
        if (_current.value != null) return // Avoid multiple calls

        _isLoading.value = true

        viewModelScope.launch {
            try {
                val response = repository.getWeather()
                _current.value = response

                Log.i("FETCH  CURRENT -> ", _current.value!!.toString())

            } catch (e: Exception) {
                Log.e("ERRO FETCH", e.toString())
            } finally {
                _isLoading.value = false
            }
        }
    }
}