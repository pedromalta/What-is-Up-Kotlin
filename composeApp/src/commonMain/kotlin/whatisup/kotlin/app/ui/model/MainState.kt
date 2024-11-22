package whatisup.kotlin.app.ui.model

import whatisup.kotlin.app.domain.DataSource

data class MainState(
    val loading: Boolean = false,
    val totalCount: Int = DataSource.UNKNOWN_TOTAL_ITEM_COUNT,
    val currentPage: Int = 0,
    val repos: List<Repo> = emptyList(),
    val pullRequests: PullRequests? = null,
)
