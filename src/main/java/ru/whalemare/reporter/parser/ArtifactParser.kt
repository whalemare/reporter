package ru.whalemare.reporter.parser

import ru.whalemare.reporter.model.Artifact
import ru.whalemare.reporter.model.Provider

/**
 * @since 2019
 * @author Anton Vlasov - whalemare
 */
class ArtifactParser(
    private val rawDependencies: String,
    private val prefixes: List<String> = listOf(
        "implementation",
        "annotationProcessor",
        "kapt",
        "api",
        "testImplementation",
        "androidTestImplementation"
    ),
    private val blacklist: List<String> = listOf(
        "fileTree(dir: 'libs', include: ['*.jar'])"
    ),
    private val trimmer: (input: String) -> String = {
        var result = it
        result = result.trim()
        result = result.removePrefix("(")
        result = result.removeSuffix(")")
        result = result.removeSurrounding("\'")
        result = result.removeSurrounding("\"")
        result = result.removeSurrounding("\\`")
        result = result.removePrefix("{")
        result
    }
) : Provider<List<Artifact>> {

    /**
     *  group: 'org.apache.poi', name: 'poi-ooxml', version: '4.1.0'
     *  to
     *
     */
    private fun parseGroup(scheme: String): Artifact? {
        val array = scheme.split(",")
        return if (array.size >= 2) {
            val parts = array.map { part ->
                part.substringAfter(":")
            }.map { part ->
                trimmer.invoke(part)
            }

            Artifact(
                scheme = parts.joinToString(":"),
                group = parts[0],
                name = parts[1],
                version = parts.getOrNull(2) ?: ""
            )
        } else {
            null
        }
    }

    override fun provide(): List<Artifact> {
        return rawDependencies.trimIndent()
            .split("\n")
            .filter {
                blacklist.forEach { blacked ->
                    if (it.contains(blacked)) {
                        return@filter false
                    }
                }

                prefixes.forEach { prefix ->
                    if (it.contains(prefix)) {
                        return@filter true
                    }
                }
                return@filter false
            }
            .map { raw ->
                var withoutPrefix = raw
                prefixes.forEach { prefix ->
                    withoutPrefix = withoutPrefix.removePrefix(prefix)
                }

                trimmer.invoke(withoutPrefix)
            }
            .map {
                val artifact = parseGroup(it)
                if (artifact == null) {
                    val array = it.split(":")
                    Artifact(it, array[0], array[1], array.getOrNull(2) ?: "")
                } else {
                    artifact
                }
            }
    }
}
