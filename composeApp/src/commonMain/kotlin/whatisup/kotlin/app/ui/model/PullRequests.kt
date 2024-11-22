package whatisup.kotlin.app.ui.model

data class PullRequests(
    override val id: Long,
    val repoId: Long,
    val userName: String,
    val repoName: String,
    val pullRequests: List<PullRequest>,
): StableId