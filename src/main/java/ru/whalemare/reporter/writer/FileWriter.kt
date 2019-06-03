package ru.whalemare.reporter.writer

import com.squareup.moshi.Moshi
import ru.whalemare.reporter.model.Dependency
import ru.whalemare.reporter.utils.DateAdapter
import ru.whalemare.reporter.utils.LocalDateTimeAdapter
import ru.whalemare.reporter.utils.toJson
import java.io.File

/**
 * @since 2019
 * @author Anton Vlasov - whalemare
 */
class FileWriter(private val path: String = "report.json"):
    Writer<Dependency> {

    override fun write(items: List<Dependency>) {
        val moshi = Moshi.Builder()
            .add(LocalDateTimeAdapter())
            .add(DateAdapter())
            .build()
        val json = moshi.toJson(items)
        val file = File(path)

        file.writeText(json)
    }
}
