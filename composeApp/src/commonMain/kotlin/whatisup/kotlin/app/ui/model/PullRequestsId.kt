package whatisup.kotlin.app.ui.model

/**
 * Argument transfer Data for Navigation
 */
data class PullRequestsId(
    val repoId: Long,
    val owner: String,
    val repo: String
)