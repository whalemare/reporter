
import model.Artifact
import model.Dependency
import model.PackageRepository
import model.Provider
import parser.ArtifactParser
import repository.MavenRepository
import writer.ConsoleWriter
import writer.ExcelWriter
import writer.FileWriter

/**
 * @since 2019
 * @author Anton Vlasov - whalemare
 */

fun main() {
    val parser: Provider<List<Artifact>> = ArtifactParser(
        """
    implementation 'com.android.support:support-compat:28.0.0'
    """.trimIndent()
    )
    val artifacts = parser.provide()
    val repositories = listOf<PackageRepository>(
        MavenRepository()
    )
    val writers = listOf(
        ConsoleWriter(),
        FileWriter(),
        ExcelWriter()
    )

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
