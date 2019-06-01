
package repository.mvn

import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import java.util.*

/**
 * Central interface for accessing [https://mvnrepository.com].
 */
interface MvnRepositoryApi {

    /**
     * Retrieves the available indexed repositories in the server.
     *
     * Note that this only returns the top 20 indexed repository.
     */
    fun getRepositories(): List<Repository>

    /**
     * Retrieves the artifact corresponding to the address formed by the [groupId]:[artifactId]:[version].
     */
    fun getArtifact(groupId: String,
                    artifactId: String,
                    version: String): Optional<MavenArtifact>

    /**
     * Retrieves the available indexed versions for the [groupId]:[artifactId].
     */
    fun getArtifactVersions(groupId: String, artifactId: String): List<String>

    /**
     * Searches the repository based from the provided [query].
     */
    fun search(query: String, page: Int = 1): Page<ArtifactEntry>

    companion object Factory {

        /**
         * Constructs a [MvnRepositoryApi] that fetches data from [https://mvnrepository.com/]
         * with a default http client.
         */
        fun create(url: HttpUrl = HttpUrl.parse("https://mvnrepository.com/")!!,
                   okHttpClient: OkHttpClient.Builder = OkHttpClient.Builder()): MvnRepositoryApi {
            okHttpClient.addInterceptor(
                LoggingInterceptor.Builder()
                    .loggable(true)
                    .setLevel(Level.HEADERS)
                    .log(0)
                    .request("Request")
                    .response("Response")
                    .logger { level, tag, message ->
                        println(message)
                    }
                    .build()
            )

            return ScrapingMvnRepositoryApi(url, okHttpClient.build())
        }
    }
}
