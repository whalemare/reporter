package ru.whalemare.reporter.model

/**
 * Describes artifact scheme like:
 *
 * @param scheme com.android.support.test.espresso:espresso-core:2.2.2
 * @param group com.android.support.test.espresso
 * @param name espresso-core
 * @param version 2.2.2
 *
 * @since 2019
 * @author Anton Vlasov - whalemare
 */
data class Artifact(
    val scheme: String,
    val group: String,
    val name: String,
    val version: String
)
