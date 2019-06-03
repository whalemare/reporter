package ru.whalemare.reporter.utils

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.text.SimpleDateFormat
import java.util.*

class DateAdapter {

    @ToJson
    fun toJson(value: Date): String {
        return FORMATTER.format(value)
    }

    @FromJson
    fun fromJson(value: String): Date {
        return FORMATTER.parse(value) as Date
    }

    companion object {
        private val FORMATTER = SimpleDateFormat("d MMMM yyyy")
    }
}
