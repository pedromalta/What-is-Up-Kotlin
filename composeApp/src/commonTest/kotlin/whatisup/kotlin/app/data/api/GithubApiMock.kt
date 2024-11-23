package whatisup.kotlin.app.data.api

import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.mock
import kotlinx.serialization.json.Json
import whatisup.kotlin.app.data.api.models.PullRequestApiModel
import whatisup.kotlin.app.data.api.models.RepositoryListApiModel
import whatisup.kotlin.app.data.api.services.GithubApi

private val json = Json {
    ignoreUnknownKeys = true
    isLenient = true
}

private val mockApiResultPage1 = json.decodeFromString<RepositoryListApiModel>(kotlinReposPage1)
private val mockApiResultPage2 = json.decodeFromString<RepositoryListApiModel>(kotlinReposPage2)
private val mockApiResultPullRequests1 =
    json.decodeFromString<List<PullRequestApiModel>>(kotlinReposPullRequests1)


val githubApiMock = mock<GithubApi> {
    everySuspend { searchRepositories(page = 1) } returns mockApiResultPage1
    everySuspend { searchRepositories(page = 2) } returns mockApiResultPage2
    everySuspend {
        getRepoPullRequests(
            repoId = 2178268611,
            owner = "JetBrains",
            repo = "kotlin"
        )
    } returns mockApiResultPullRequests1
}
