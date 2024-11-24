package whatisup.kotlin.app.domain.models

/**
 * Data model for a list of Repository
 */
data class PullRequestsModel(
    val repoId: Long,
    val userName: String,
    val repoName: String,
    val pullRequests: List<PullRequestModel>,
) {
    companion object {

        /**
         * Represents an empty PullRequestsModel
         */
        val EMPTY = PullRequestsModel(0, "", "", emptyList())
    }
}