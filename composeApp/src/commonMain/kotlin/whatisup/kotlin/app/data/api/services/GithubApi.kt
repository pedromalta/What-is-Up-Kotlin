package whatisup.kotlin.app.data.api.services

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.appendPathSegments
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import whatisup.kotlin.app.data.api.models.PullRequestApiModel
import whatisup.kotlin.app.data.api.models.RepositoryListApiModel

/**
 * GitHub API
 */
interface GithubApi {

    /**
     * Requests a paged list of repositories filtering by [language] and sorting by [sort]
     */
    suspend fun searchRepositories(
        language: String = "Kotlin",
        sort: String = "stars",
        page: Int,
    ): RepositoryListApiModel

    /**
     * Requests the last Pull Requests for a given repository
     */
    suspend fun getRepoPullRequests(
        repoId: Long,
        owner: String,
        repo: String
    ): List<PullRequestApiModel>
}

class GithubApiImpl(
    private val dispatcher: CoroutineDispatcher,
    private val client: HttpClient
) : GithubApi {

    override suspend fun searchRepositories(
        language: String,
        sort: String,
        page: Int
    ): RepositoryListApiModel {
        return withContext(dispatcher) {
            // Example URL: https://api.github.com/search/repositories?q=language:Kotlin&sort=stars&page=1

            client.get {
                url {
                    appendPathSegments("search", "repositories")
                    parameters.append("q", "language:$language")
                    parameters.append("sort", sort)
                    parameters.append("page", page.toString())

                }
            }.body<RepositoryListApiModel>()
        }
    }

    override suspend fun getRepoPullRequests(repoId: Long, owner: String, repo: String): List<PullRequestApiModel> {
        return withContext(dispatcher) {
            // Example URL: https://api.github.com/repos/JetBrains/kotlin/pulls

            client.get {
                url {
                    appendPathSegments("repos", owner, repo, "pulls")
                }
            }.body<List<PullRequestApiModel>>().map {
                it.copy(repoId = repoId)
            }
        }
    }


}