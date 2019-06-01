package writer

import com.squareup.moshi.Moshi
import model.Dependency
import toJson
import utils.LocalDateTimeAdapter
import java.io.File

/**
 * @since 2019
 * @author Anton Vlasov - whalemare
 */
class FileWriter(private val path: String = "report.json"): Writer<Dependency> {

    override fun write(items: List<Dependency>) {
        val moshi = Moshi.Builder()
            .add(LocalDateTimeAdapter())
            .build()
        val json = moshi.toJson(items)
        val file = File(path)

        file.writeText(json)
    }
}
