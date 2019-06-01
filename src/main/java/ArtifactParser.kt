import model.Package

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
    )
) : Provider<List<Package>> {

    override fun provide(): List<Package> {
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

                withoutPrefix = withoutPrefix.trim()
                withoutPrefix = withoutPrefix.removeSurrounding("\'")
                withoutPrefix = withoutPrefix.removeSurrounding("\"")
                withoutPrefix = withoutPrefix.removeSurrounding("\\`")
                withoutPrefix
            }
            .map {
                val array = it.split(":")
                Package(it, array[0], array[1], array[2])
            }
    }
}
