package pt.fabiomatos.weatherapp.utils

import android.os.Build
import androidx.annotation.RequiresApi
import pt.fabiomatos.weatherapp.ListDay
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

object Utils {

    @RequiresApi(Build.VERSION_CODES.O)
    fun formatDayWeather(date: LocalDate, outputFormat: DateTimeFormatter): String {

        // Get today's date and tomorrow's date
        val today = LocalDate.now()
        val tomorrow = today.plusDays(1)

        val dayOfWeek = date.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault())

        // Format the LocalDateTime to a string
        val formattedDate = date.format(outputFormat)

        // Determine if the date is today, tomorrow, or another day
        val result = when (date) {
            today -> "Today"
            tomorrow -> "Tomorrow"
            else -> dayOfWeek
        }

        return "$result, $formattedDate"
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun formatLocalDate(date: LocalDate, outputFormat: DateTimeFormatter): String {
        return date.format(outputFormat)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getHourFromDateTime(dateTimeString: String): Int {
        val dateTime = LocalDateTime.parse(dateTimeString, Constants.YYYY_MM_DD_HH_MM_SS)
        return dateTime.hour
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun GroupItemsByDay(items: List<ListDay>): Map<LocalDate, List<ListDay>> {
        val formatter = DateTimeFormatter.ISO_DATE_TIME
        return items.groupBy { LocalDateTime.parse(LocalDateTime.ofInstant(Instant.ofEpochSecond(it.dt!!), ZoneId.systemDefault())
            .toString(), formatter).toLocalDate() }
    }
}