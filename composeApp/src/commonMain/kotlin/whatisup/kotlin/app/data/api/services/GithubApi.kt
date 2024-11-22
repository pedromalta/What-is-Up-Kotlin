package whatisup.kotlin.app.data.api.services

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.appendPathSegments
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import whatisup.kotlin.app.data.api.models.RepoList
import whatisup.kotlin.app.data.api.models.RepoPullRequest

interface GithubApi {
    suspend fun searchRepositories(
        language: String = "Kotlin",
        sort: String = "stars",
        page: Int,
    ): RepoList

    suspend fun getRepoPullRequests(
        owner: String,
        repo: String
    ): List<RepoPullRequest>
}

class GithubApiImpl(
    private val dispatcher: CoroutineDispatcher,
    private val client: HttpClient
) : GithubApi {

    override suspend fun searchRepositories(
        language: String,
        sort: String,
        page: Int
    ): RepoList {
        return withContext(dispatcher) {
            // Example URL: https://api.github.com/search/repositories?q=language:Kotlin&sort=stars&page=1

            client.get {
                url {
                    appendPathSegments("search", "repositories")
                    parameters.append("q", "language:$language")
                    parameters.append("sort", sort)
                    parameters.append("page", page.toString())

                }
            }.body<RepoList>()
        }
    }

    override suspend fun getRepoPullRequests(owner: String, repo: String): List<RepoPullRequest> {
        return withContext(dispatcher) {
            // Example URL: https://api.github.com/repos/JetBrains/kotlin/pulls

            client.get {
                url {
                    appendPathSegments("repos", owner, repo, "pulls")
                }
            }.body<List<RepoPullRequest>>()
        }
    }


}