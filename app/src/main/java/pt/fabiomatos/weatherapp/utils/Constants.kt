package pt.fabiomatos.weatherapp.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.format.DateTimeFormatter

object Constants {
    @RequiresApi(Build.VERSION_CODES.O)
    val EEE_DD_MMMM: DateTimeFormatter = DateTimeFormatter.ofPattern("EEEE, dd MMMM")
}