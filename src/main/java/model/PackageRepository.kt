package model

/**
 * @since 2019
 * @author Anton Vlasov - whalemare
 */
interface PackageRepository {
    fun search(artifact: Artifact): Dependency
}
