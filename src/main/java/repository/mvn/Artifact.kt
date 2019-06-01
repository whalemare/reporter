
package repository.mvn

import java.net.URI
import java.time.LocalDate

/**
 * Represents a Maven artifact.
 *
 * In a typical [pom.xml], this corresponds to the dependency section:
 * ```
 *  <dependency>
 *      <groupId>...</groupId>
 *      <artifactId>...</artifactId>
 *      <version>...</version>
 *  </dependency>
 * ```
 */
data class Artifact(
    val groupId: String,
    val id: String,
    val version: String,
    val license: String,
    val homepage: URI,
    val date: LocalDate,
    val snippets: List<Snippet>
)
