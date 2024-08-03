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
    fun convertTimestampToDate(timestamp: Long): String {

        val dateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp), ZoneId.systemDefault())
        val date = dateTime.toLocalDate()

        // Get today's date and tomorrow's date
        val today = LocalDate.now()
        val tomorrow = today.plusDays(1)

        val dayOfWeek = dateTime.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault())

        // Format the LocalDateTime to a string
        val formatter = DateTimeFormatter.ofPattern("MMM dd")
        val formattedDate = dateTime.format(formatter)

        // Determine if the date is today, tomorrow, or another day
        val result = when (date) {
            today -> "Today"
            tomorrow -> "Tomorrow"
            else -> dayOfWeek
        }

        return "$result, $formattedDate"
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun parseIsoDateTime(timestamp: String): LocalDateTime {
        val formatter = DateTimeFormatter.ISO_DATE_TIME
        return LocalDateTime.parse(timestamp, formatter)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun formatLocalDate(date: LocalDate, outputFormat: DateTimeFormatter): String {
        return date.format(outputFormat)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun GroupItemsByDay(items: List<ListDay>): Map<LocalDate, List<ListDay>> {
        val formatter = DateTimeFormatter.ISO_DATE_TIME
        return items.groupBy { LocalDateTime.parse(LocalDateTime.ofInstant(Instant.ofEpochSecond(it.dt!!), ZoneId.systemDefault())
            .toString(), formatter).toLocalDate() }
    }
}