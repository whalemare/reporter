package writer

import com.squareup.moshi.Moshi
import model.Dependency
import toJson
import utils.LocalDateTimeAdapter

/**
 * @since 2019
 * @author Anton Vlasov - whalemare
 */
class ConsoleWriter: Writer<Dependency> {

    override fun write(items: List<Dependency>) {
        val moshi = Moshi.Builder()
            .add(LocalDateTimeAdapter())
            .build()
        val json = moshi.toJson(items)

        println(json)
    }
}
