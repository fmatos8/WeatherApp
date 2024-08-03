package pt.fabiomatos.weatherapp.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.format.DateTimeFormatter

object Constants {
    @RequiresApi(Build.VERSION_CODES.O)
    val EEE_DD_MMMM: DateTimeFormatter = DateTimeFormatter.ofPattern("EEEE, dd MMMM")
    @RequiresApi(Build.VERSION_CODES.O)
    val MMM_DD: DateTimeFormatter = DateTimeFormatter.ofPattern("MMM dd")
    @RequiresApi(Build.VERSION_CODES.O)
    val HH: DateTimeFormatter = DateTimeFormatter.ofPattern("HH")
    @RequiresApi(Build.VERSION_CODES.O)
    val YYYY_MM_DD_HH_MM_SS: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
}