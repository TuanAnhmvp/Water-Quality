package com.example.hauiwaterquality.utils

import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.text.SimpleDateFormat
import java.util.*


@BindingAdapter("app:setTextsTemperature")
fun TextView.setTextsTemperature(float: Float) {
    text = buildString {
        append(float)
        append("°C")
    }
}

@BindingAdapter("app:setTextTime")
fun TextView.setTextTime(time: Long) {
    val result = epochToIso8601(time)
    text = buildString {
        append(result)
    }
}

private fun epochToIso8601(time: Long): String {
    val format = "dd MMM yyyy - HH:mm:ss" // you can add the format you need
    val sdf = SimpleDateFormat(format, Locale.getDefault()) // default local
    sdf.timeZone = TimeZone.getDefault() // set anytime zone you need
    return sdf.format(Date(time * 1000))
}


