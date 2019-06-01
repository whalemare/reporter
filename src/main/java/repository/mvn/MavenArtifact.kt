
package repository.mvn

import java.net.URI

/**
 * Represents a Maven artifact.
 *
 * In a typical [pom.xml], this corresponds to the dependency section:
 * ```
 *  <dependency>
 *      <group>...</group>
 *      <artifact>...</artifact>
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
