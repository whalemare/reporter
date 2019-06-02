package ru.whalemare.reporter

import ru.whalemare.reporter.model.Artifact
import ru.whalemare.reporter.model.Dependency
import ru.whalemare.reporter.model.PackageRepository
import ru.whalemare.reporter.model.Provider
import ru.whalemare.reporter.parser.ArtifactParser
import ru.whalemare.reporter.repository.MavenRepository
import ru.whalemare.reporter.writer.ConsoleWriter
import ru.whalemare.reporter.writer.ExcelWriter
import ru.whalemare.reporter.writer.FileWriter
import ru.whalemare.reporter.writer.Writer

/**
 * @since 2019
 * @author Anton Vlasov - whalemare
 */
class Reporter(
    private val rawDependencies: List<String>,
    private val parser: Provider<List<Artifact>> = ArtifactParser(
        rawDependencies.joinToString("\n")
    ),
    private val repositories: List<PackageRepository> = listOf(MavenRepository()),
    private val writers: List<Writer<Dependency>> = listOf(
        ConsoleWriter(),
        FileWriter(),
        ExcelWriter()
    )
): Runnable {

    constructor(rawDependencies: String) : this(rawDependencies.lines())

    override fun run() {
        val artifacts = parser.provide()
        val dependencies = mutableListOf<Dependency>()

        artifacts.mapIndexed { index, artifact ->
            println("Process ${index + 1}/${artifacts.size}: ${artifact.scheme}")
            repositories.map { repository ->
                try {
                    dependencies.add(repository.search(artifact))
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

        writers.forEach { writer ->
            writer.write(dependencies)
        }
    }
}
