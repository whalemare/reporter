
package ru.whalemare.reporter.repository.mvn

import java.net.URI
import java.time.LocalDate

/**
 * Represents a Maven name.
 *
 * In a typical [pom.xml], this corresponds to the dependency section:
 * ```
 *  <dependency>
 *      <group>...</group>
 *      <name>...</name>
 *      <version>...</version>
 *  </dependency>
 * ```
 */
data class MavenArtifact(
    val groupId: String,
    val id: String,
    val version: String,
    val license: String,
    val homepage: URI,
//    val date: LocalDate,
    val snippets: List<Snippet>
)
