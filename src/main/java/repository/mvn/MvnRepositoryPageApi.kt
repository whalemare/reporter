
package repository.mvn

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

    @GET("/artifact/{groupId}/{artifactId}")
    fun getArtifactVersionsPage(@Path("groupId") groupId: String,
                                @Path("artifactId") artifactId: String): Call<ArtifactVersionsPage>

    @GET("/artifact/{groupId}/{artifactId}/{version}")
    fun getArtifactPage(@Path("groupId") groupId: String,
                        @Path("artifactId") artifactId: String,
                        @Path("version") version: String): Call<ArtifactPage>

}
