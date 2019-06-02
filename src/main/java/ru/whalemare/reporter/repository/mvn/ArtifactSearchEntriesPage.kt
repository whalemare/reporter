
package ru.whalemare.reporter.repository.mvn

import org.jsoup.nodes.Element
import pl.droidsonroids.jspoon.ElementConverter
import pl.droidsonroids.jspoon.annotation.Selector
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

internal class ArtifactSearchEntriesPage {

    @Selector("#maincontent > div.im")
    lateinit var entries: List<Entry>

    @Selector("#maincontent > h2 > b")
    var totalResults: Long = 0L

    internal class Entry {

        @Selector("div.im-header > p > a:nth-child(1)")
        var groupId: String? = null

        @Selector("div.im-header > p > a:nth-child(2)")
        var artifactId: String? = null

        @Selector("div.im-header > p > span")
        var license: String? = null

        @Selector("div.im-description")
        var description: String? = null

        @Selector("div.im-description > div", converter = ReleaseDateElementConverter::class)
        var releaseDate: LocalDate? = null

        /**
         * @return whether this object is properly populated from the page
         */
        fun isPopulated(): Boolean = groupId != null && artifactId != null

        companion object ReleaseDateElementConverter : ElementConverter<LocalDate> {

            private const val PREFIX = "Last Release on "

            override fun convert(root: Element?, selector: Selector): LocalDate? {
                val node = root?.selectFirst(selector.value)?.text() ?: return null
                val str = node.substringAfter(PREFIX)
                return LocalDate.parse(str, DateTimeFormatter.ofPattern("MMM d, yyyy", Locale.US))
            }

        }

    }
}
