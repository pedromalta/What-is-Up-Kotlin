package whatisup.kotlin.app.ui.model

data class PullRequestsId(
    val repoId: Long,
    val owner: String,
    val repo: String
)