package repository

import model.Artifact
import model.Dependency
import model.PackageRepository
import repository.mvn.MvnRepositoryApi



/**
 * @since 2019
 * @author Anton Vlasov - whalemare
 */
class MavenRepository: PackageRepository {

    private val api = MvnRepositoryApi.create()

    override fun search(artifact: Artifact): Dependency {
        val page = api.search(artifact.scheme)
        val mavenArtifact = page.items.first()
        return Dependency(
            name = mavenArtifact.id,
            description = mavenArtifact.description,
            url = mavenArtifact.id,
            artifact = artifact,
            lastRelease = mavenArtifact.lastRelease
        )
    }
}
