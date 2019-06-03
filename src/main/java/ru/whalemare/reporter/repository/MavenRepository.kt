package ru.whalemare.reporter.repository

import ru.whalemare.reporter.model.Artifact
import ru.whalemare.reporter.model.Dependency
import ru.whalemare.reporter.model.PackageRepository
import ru.whalemare.reporter.repository.mvn.ArtifactEntry
import ru.whalemare.reporter.repository.mvn.MvnRepositoryApi


/**
 * @since 2019
 * @author Anton Vlasov - whalemare
 */
class MavenRepository : PackageRepository {

    private val api = MvnRepositoryApi.create()

    override fun search(artifact: Artifact): Dependency {
//        val mavenArtifact = list(name).first()
        val mavenArtifact = api.getArtifact(artifact.group, artifact.name, artifact.version).get()

        val version = if (artifact.version.isBlank()) mavenArtifact.version else artifact.version
        return Dependency(
            name = mavenArtifact.name,
            description = mavenArtifact.description,
            url = mavenArtifact.homepage.toString(),
            artifact = artifact.copy(version = version),
            license = mavenArtifact.license,
            releaseDate = mavenArtifact.date
        )
    }

    fun list(artifact: Artifact): List<ArtifactEntry> {
        val page = api.search(artifact.scheme)
        return page.items
    }
}
