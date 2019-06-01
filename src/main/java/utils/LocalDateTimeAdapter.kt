package utils

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class LocalDateTimeAdapter {

    @ToJson
    fun toJson(value: LocalDate): String {
        return FORMATTER.format(value)
    }

    @FromJson
    fun fromJson(value: String): LocalDate {
        return FORMATTER.parse(value) as LocalDate
    }

    companion object {
        private val FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE
    }
}
