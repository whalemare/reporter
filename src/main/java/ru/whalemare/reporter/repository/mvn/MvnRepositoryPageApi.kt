
package ru.whalemare.reporter.repository.mvn

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

internal interface MvnRepositoryPageApi {

    @GET("/search")
    fun search(@Query("q") query: String,
               @Query("p") page: Int,
               @Query("sort") sort: String): Call<ArtifactSearchEntriesPage>

    @GET("/repos")
    fun getRepositoriesPage(@Query("p") page: Int): Call<RepositoriesPage>

    @GET("/artifact/{group}/{artifact}")
    fun getArtifactVersionsPage(@Path("group") groupId: String,
                                @Path("artifact") artifactId: String): Call<ArtifactVersionsPage>

    @GET("/artifact/{group}/{artifact}/{version}")
    fun getArtifactPage(@Path("group") groupId: String,
                        @Path("artifact") artifactId: String,
                        @Path("version") version: String): Call<ArtifactPage>

}
