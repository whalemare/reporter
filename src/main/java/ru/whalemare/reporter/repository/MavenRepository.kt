package ru.whalemare.reporter.repository

import ru.whalemare.reporter.model.Artifact
import ru.whalemare.reporter.model.Dependency
import ru.whalemare.reporter.model.PackageRepository
import ru.whalemare.reporter.repository.mvn.ArtifactEntry
import ru.whalemare.reporter.repository.mvn.MavenArtifact
import ru.whalemare.reporter.repository.mvn.MvnRepositoryApi
import java.util.*


/**
 * @since 2019
 * @author Anton Vlasov - whalemare
 */
class MavenRepository : PackageRepository {

    private val api = MvnRepositoryApi.create()

    override fun search(artifact: Artifact): Dependency {
//        val mavenArtifact = list(name).first()
        val mavenArtifact = one(artifact).get()

        return return Dependency(
            name = mavenArtifact.id,
//            description = mavenArtifact.description,
            description = "",
            url = mavenArtifact.id,
            artifact = artifact,
            license = mavenArtifact.license
//            lastRelease = mavenArtifact.lastRelease
        )
    }

    fun one(artifact: Artifact): Optional<MavenArtifact> {
        return api.getArtifact(artifact.group, artifact.name, artifact.version)
    }

    fun list(artifact: Artifact): List<ArtifactEntry> {
        val page = api.search(artifact.scheme)
        return page.items
    }
}
