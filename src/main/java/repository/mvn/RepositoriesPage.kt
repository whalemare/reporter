
package repository.mvn

import org.jsoup.nodes.Element
import pl.droidsonroids.jspoon.ElementConverter
import pl.droidsonroids.jspoon.annotation.Selector
import java.net.URI

internal class RepositoriesPage {

    @Selector("#maincontent > div.im")
    lateinit var entries: List<Entry>

    internal class Entry {

        @Selector("div.im-header > h2 > a", converter = IdConverter::class)
        var id: String? = null

        @Selector("div.im-header > h2 > a")
        var name: String? = null

        @Selector("div.im-header > p", converter = UriElementConverter::class)
        var uri: URI? = null

        /**
         * @return whether this object is properly populated from the page
         */
        fun isPopulated(): Boolean = id != null && name != null && uri != null

        companion object IdConverter : ElementConverter<String> {

            override fun convert(node: Element?, selector: Selector): String? {
                val href = node?.selectFirst(selector.value)?.attr("href")
                // hrefs are in the form of: /repos/{id}
                return href?.substringAfterLast("/")
            }

        }
    }
}
