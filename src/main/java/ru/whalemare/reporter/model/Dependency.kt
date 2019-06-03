package ru.whalemare.reporter.model

import java.util.*

/**
 * @since 2019
 * @author Anton Vlasov - whalemare
 */
data class Dependency(
    val name: String,
    val description: String,
    val url: String,
    val artifact: Artifact,
    val license: String? = null,
    val releaseDate: Date? = null
)
