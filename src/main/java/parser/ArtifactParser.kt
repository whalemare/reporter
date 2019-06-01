package parser

import model.Artifact
import model.Provider

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
        result = result.removeSurrounding("\'")
        result = result.removeSurrounding("\"")
        result = result.removeSurrounding("\\`")
        result = result.removeSuffix("(")
        result = result.removePrefix(")")
        result = result.removePrefix("{")
        result
    }
) : Provider<List<Artifact>> {

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
                val array = it.split(":")
                Artifact(it, array[0], array[1], array[2])
            }
    }
}
