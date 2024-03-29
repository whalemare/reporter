package ru.whalemare.reporter.repository.mvn

import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import org.funktionale.memoization.memoize
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import pl.droidsonroids.retrofit2.JspoonConverterFactory
import retrofit2.Retrofit
import java.util.*

internal class ScrapingMvnRepositoryApi(
    private val baseUrl: HttpUrl,
    private val okHttpClient: OkHttpClient
) : MvnRepositoryApi {

    companion object {

        const val MAX_LIMIT = 10 // Pages are always in 10 entries
        const val MAX_PAGE = 50 // Site throws a 404 when exceeding 50
    }

    private val logger: Logger = LoggerFactory.getLogger(MvnRepositoryApi::class.java)
    private val pageApi: MvnRepositoryPageApi

    private val repositories: () -> List<Repository>

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(JspoonConverterFactory.create())
            .validateEagerly(true)
            .build()
        pageApi = retrofit.create(MvnRepositoryPageApi::class.java)

        // Repositories are unlikely to change, so we memoize them instead of fetching them every time
        repositories = {
            var p = 1
            val repos = mutableListOf<Repository>()
            while (true) { // there are only 2(?) pages, we'll query them all at once now
                val response = pageApi.getRepositoriesPage(p).execute()
                p++ // next page
                if (!response.isSuccessful) {
                    logger.warn("Request to $baseUrl failed while fetching repositories, got: ${response.code()}")
                    break
                }
                val page = response.body() ?: break;
                if (page.entries.isEmpty())
                    break // stop when the page no longer shows an entry (we exceeded max page)

                repos.addAll(page.entries
                    .filter { it.isPopulated() }
                    .map { Repository(it.id!!, it.name!!, it.uri!!) })
            }
            repos.toList()
        }.memoize()
    }

    override fun getRepositories(): List<Repository> = repositories()

    override fun getArtifactVersions(groupId: String, artifactId: String): List<String> {
        val response = pageApi.getArtifactVersionsPage(groupId, artifactId).execute()
        if (!response.isSuccessful) {
            logger.warn(
                "Request to $baseUrl failed while fetching versions for name '" +
                        "$groupId:$artifactId', got: ${response.code()}"
            )
            return emptyList()
        }

        val body = response.body() ?: return emptyList()
        return body.versions
    }

    override fun getArtifact(groupId: String, artifactId: String, version: String): Optional<MavenArtifact> {
        val response = pageApi.getArtifactPage(groupId, artifactId, version).execute()
        if (!response.isSuccessful) {
            logger.warn(
                "Request to $baseUrl failed while fetching name '" +
                        "$groupId:$artifactId:$version', got: ${response.code()}"
            )
            return Optional.empty()
        }
        val body = response.body() ?: return Optional.empty()
//        val localDate = body.date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        val artifact = MavenArtifact(
            groupId = groupId,
            id = artifactId,
            name = body.name,
            usedBy = body.usedBy,
            version = version,
            license = body.license,
            homepage = body.homepage,
            description = body.description,
            snippets = body.snippets,
            date = body.releaseDate
        )

        return Optional.of(artifact)
    }

    override fun search(query: String, page: Int): Page<ArtifactEntry> {
        if (page < 1 || page > MAX_PAGE)
            return Page.empty()

        val response = pageApi.search(query, page, "relevance").execute()
        if (!response.isSuccessful) {
            logger.warn("Request to $baseUrl failed while searching for '$query' on page '$page'")
            return Page.empty()
        }

        val body = response.body() ?: return Page.empty()
        val entries = body.entries
            .filter { it.isPopulated() }
            .map {
                ArtifactEntry(
                    it.groupId!!,
                    it.artifactId!!,
                    it.license,
                    it.description!!,
                    it.releaseDate!!
                )
            }

        val totalPages = Math.min(
            Math.ceil((body.totalResults / MAX_LIMIT).toDouble()).toInt(),
            MAX_PAGE
        )
        return Page(
            page,
            MAX_LIMIT,
            entries.toList(),
            totalPages,
            body.totalResults
        )
    }
}
