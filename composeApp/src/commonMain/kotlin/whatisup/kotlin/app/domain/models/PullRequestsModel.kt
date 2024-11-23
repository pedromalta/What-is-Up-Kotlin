package whatisup.kotlin.app.domain.models

data class PullRequestsModel(
    val repoId: Long,
    val userName: String,
    val repoName: String,
    val pullRequests: List<PullRequestModel>,
) {
    companion object {
        val EMPTY = PullRequestsModel(0, "", "", emptyList())
    }
}