package model

/**
 * @since 2019
 * @author Anton Vlasov - whalemare
 */
data class Dependency(
    val name: String,
    val description: String,
    val url: String,
    val aArtifact: Package
)
