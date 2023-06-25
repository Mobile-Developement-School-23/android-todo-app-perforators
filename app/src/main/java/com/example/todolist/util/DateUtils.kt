package com.example.todolist.util

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

private val defaultDateFormat = SimpleDateFormat("dd MMM yyyy", Locale("ru"))

fun Date.convertToString(dateFormat: SimpleDateFormat = defaultDateFormat): String {
    return dateFormat.format(this)
}

fun String.convertToDate(dateFormat: SimpleDateFormat = defaultDateFormat): Date? {
    return dateFormat.parse(this)
}

fun convertToString(day: Int, month: Int, year: Int): String {
    val calendar = Calendar.getInstance()
    calendar.set(year, month, day)
    return calendar.time.convertToString()
}