
package ru.whalemare.reporter.repository.mvn

import java.net.URI
import java.util.*

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
    val description: String,
    val date: Date?,
    val snippets: List<Snippet>,
    val name: String,
    val usedBy: String
)
