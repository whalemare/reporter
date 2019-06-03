package ru.whalemare.reporter.repository.mvn

import org.jsoup.nodes.Element
import pl.droidsonroids.jspoon.ElementConverter
import pl.droidsonroids.jspoon.annotation.Selector
import java.net.URI
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.util.*


internal class ArtifactPage {

    @Selector("#maincontent > table > tbody > tr:nth-child(1) > td > span")
    var license: String = ""

    @Selector(
        "#maincontent > table > tbody > tr:nth-child(3) > td > a",
        attr = "href", converter = UriElementConverter::class
    )
    var homepage: URI = URI.create("")

    @Selector("#snippets", converter = SnippetElementConverter::class)
    var snippets: List<Snippet> = emptyList()

    @Selector("#maincontent > div.im > div.im-description")
    var description: String = ""

    @Selector("#maincontent > table > tbody > tr:nth-child(4) > td > a > b")
    var usedBy: String = ""

    @Selector("#maincontent > div.im > div.im-header > h2 > a")
    var name: String = ""

    @Selector("#snippets > div > div > div > table", converter = ReleaseDateElementConverter::class)
    var releaseDate: Date? = null

    internal class SnippetElementConverter : ElementConverter<List<Snippet>> {

        override fun convert(node: Element?, selector: Selector): List<Snippet>? {
            val elem = node?.selectFirst(selector.value) ?: return emptyList()

            // Under this element there are <textarea>s with id '$snippetType-a'
            val snippets = mutableListOf<Snippet>()
            for (type in Snippet.Type.values()) {
                val prefix = type.name.toLowerCase()
                val textarea = elem.selectFirst("#$prefix-a") ?: continue

                snippets.add(Snippet(type, textarea.`val`()))
            }
            return snippets.toList()
        }
    }

    internal class ReleaseDateElementConverter : ElementConverter<Date> {

        override fun convert(table: Element?, selector: Selector): Date? {
            if (table == null) return null

            return try {
                val textDate = table.getElementsByClass("grid")
                    .select("tr")
                    .eachText()
                    .firstOrNull { it.contains("Date (") } ?: return null

                val dateNormalize = textDate.removePrefix("Date (")
                    .removeSuffix(")")
                    .trim()
                val date = SimpleDateFormat("MMM dd, YYYY", Locale.ENGLISH)
                    .parse(dateNormalize)
                date
            } catch (e: Exception) {
                null
            }
        }

    }


    internal class LatestReleaseDateElementConverter : ElementConverter<LocalDate> {

        override fun convert(table: Element?, selector: Selector): LocalDate? {
            if (table == null) return null

            return try {
                val textDate = table.getElementsByClass("grid versions")
                    .select("tr")[1]
                    .select("td")
                    .last()
                    .text()
                val date = SimpleDateFormat("MMM, YYYY", Locale.US).parse(textDate)
                date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
            } catch (e: Exception) {
                null
            }
        }

    }
}
