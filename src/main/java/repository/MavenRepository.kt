package repository

import model.Artifact
import model.Dependency
import model.PackageRepository
import repository.mvn.ArtifactEntry
import repository.mvn.MavenArtifact
import repository.mvn.MvnRepositoryApi
import java.util.*


/**
 * @since 2019
 * @author Anton Vlasov - whalemare
 */
class MavenRepository : PackageRepository {

    private val api = MvnRepositoryApi.create()

    override fun search(artifact: Artifact): Dependency {
//        val mavenArtifact = list(artifact).first()
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
        return api.getArtifact(artifact.group, artifact.artifact, artifact.version)
    }

    fun list(artifact: Artifact): List<ArtifactEntry> {
        val page = api.search(artifact.scheme)
        return page.items
    }
}
