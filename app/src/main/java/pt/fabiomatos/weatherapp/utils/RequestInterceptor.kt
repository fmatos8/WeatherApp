package pt.fabiomatos.weatherapp.utils

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response
import pt.fabiomatos.weatherapp.MainActivity
import java.util.Locale

class QueryParameterInterceptor : Interceptor {
    private val API_KEY: String = "23078121d317ab553399bfccf25aeb27"

    val context: Context = MainActivity.applicationContext()

    override fun intercept(chain: Interceptor.Chain): Response {

        val originalRequest = chain.request()
        val originalUrl = originalRequest.url

        // Add your parameter here
        val urlWithParam = originalUrl.newBuilder()
            .addQueryParameter("lat", "41.14766290")
            .addQueryParameter("lon", "-8.60789730")
            .addQueryParameter("units", "metric")
            .addQueryParameter("appid", API_KEY)
            .build()

        // Build the new request with the new URL
        val newRequest = originalRequest.newBuilder()
            .url(urlWithParam)
            .addHeader("Accept", "application/json")
            .build()

        return chain.proceed(newRequest)
    }
}