
package ru.whalemare.reporter.repository.mvn

import org.jsoup.nodes.Element
import pl.droidsonroids.jspoon.ElementConverter
import pl.droidsonroids.jspoon.annotation.Selector
import java.net.URI

/**
 * Converts the matched element into a [URI]
 */
internal class UriElementConverter : ElementConverter<URI> {

    override fun convert(root: Element?, selector: Selector): URI? {
        val uri = root?.selectFirst(selector.value)?.attr(selector.attr)
        return if (uri != null) URI.create(uri) else null
    }

}
