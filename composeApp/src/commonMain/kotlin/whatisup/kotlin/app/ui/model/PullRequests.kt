package whatisup.kotlin.app.ui.model

/**
 * UI Data that encapsulates a list of PullRequests
 */
data class PullRequests(
    val id: Long,
    val repoId: Long,
    val userName: String,
    val repoName: String,
    val pullRequests: List<PullRequest>,
)