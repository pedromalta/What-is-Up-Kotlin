package whatisup.kotlin.app.ui.model

import whatisup.kotlin.app.domain.datasource.RepoListDataSource

data class MainState(
    val loadingRepoList: Boolean = false,
    val loadingPullRequests: Boolean = false,
    val totalCount: Int = RepoListDataSource.UNKNOWN_TOTAL_ITEM_COUNT,
    val currentPage: Int = 0,
    val repos: List<Repo> = emptyList(),
    val pullRequests: PullRequests? = null,
)
