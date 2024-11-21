package whatisup.kotlin.app.data.api

import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.mock
import kotlinx.serialization.json.Json
import whatisup.kotlin.app.data.api.models.RepoList
import whatisup.kotlin.app.data.api.services.GithubApi

private val json = Json {
    ignoreUnknownKeys = true
    isLenient = true
}

private val mockApiResultPage1 = json.decodeFromString<RepoList>(kotlinReposPage1)
private val mockApiResultPage2 = json.decodeFromString<RepoList>(kotlinReposPage2)


val githubApiMock = mock<GithubApi> {
    everySuspend { searchRepositories(page = 1) } returns mockApiResultPage1
    everySuspend { searchRepositories(page = 2) } returns mockApiResultPage2
}
