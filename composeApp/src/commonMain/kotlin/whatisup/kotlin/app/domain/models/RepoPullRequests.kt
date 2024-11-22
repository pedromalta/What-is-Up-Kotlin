package whatisup.kotlin.app.domain.models

data class RepoPullRequests(
    val repoId: Long,
    val userName: String,
    val repoName: String,
    val pullRequests: List<RepoPullRequest>,
) {
    companion object {
        val EMPTY = RepoPullRequests(0, "", "", emptyList())
    }
}