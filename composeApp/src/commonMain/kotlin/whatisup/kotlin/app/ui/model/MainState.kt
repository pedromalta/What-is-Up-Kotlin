package whatisup.kotlin.app.ui.model

import whatisup.kotlin.app.domain.datasource.RepositoriesDataSource
import whatisup.kotlin.app.ui.viewmodels.MainViewModel

/**
 * Master Detail UI state for the [MainViewModel]
 */
data class MainState(
    val loadingRepoList: Boolean = false,
    val loadingPullRequests: Boolean = false,
    val totalCount: Int = RepositoriesDataSource.UNKNOWN_TOTAL_ITEM_COUNT,
    val currentPage: Int = 0,
    val repos: Set<Repository> = setOf(),
    val pullRequests: PullRequests? = null,
)
