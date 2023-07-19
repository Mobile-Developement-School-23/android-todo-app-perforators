package com.example.commom

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

private val defaultDateFormat = SimpleDateFormat("dd MMM yyyy", Locale("ru"))
private val defaultTimeFormat = SimpleDateFormat("HH:mm", Locale("ru"))
private const val MILLIS_IN_MINUTE = 1000L * 60
private const val MILLIS_IN_HOUR = MILLIS_IN_MINUTE * 60
private const val MILLIS_IN_DAY = MILLIS_IN_HOUR * 24

fun Date.convertToString(dateFormat: SimpleDateFormat = defaultDateFormat): String {
    return dateFormat.format(this)
}

fun Long.convertToStringTime(timeFormat: SimpleDateFormat = defaultTimeFormat): String {
    return timeFormat.format(convertToDate())
}

fun Date.splitOnTimeAndDate(): Pair<String, String> {
    val dateWithoutTime = time / MILLIS_IN_DAY * MILLIS_IN_DAY
    val time = time - dateWithoutTime
    return dateWithoutTime.convertToDate().convertToString() to time.convertToStringTime()
}

fun String.convertToDate(dateFormat: SimpleDateFormat = defaultDateFormat): Date? {
    return dateFormat.parse(this)
}

fun Long.convertToDate(): Date {
    return Date(this)
}

fun convertToDate(date: String, time: String? = null): Date {
    val dateTime = if (time == null) 0L else defaultTimeFormat.parse(time).time
    val totalTime = defaultDateFormat.parse(date).time + dateTime
    return Date(totalTime)
}

fun convertToString(day: Int, month: Int, year: Int): String {
    val calendar = Calendar.getInstance()
    calendar.set(year, month, day)
    return calendar.time.convertToString()
}

fun convertToString(hour: Int, minute: Int): String {
    val totalMillis = hour * MILLIS_IN_HOUR + minute * MILLIS_IN_MINUTE
    return totalMillis.convertToStringTime()
}