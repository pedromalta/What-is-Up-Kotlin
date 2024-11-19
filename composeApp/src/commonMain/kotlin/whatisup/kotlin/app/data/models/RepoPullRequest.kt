package whatisup.kotlin.app.data.models

data class RepoPullRequest(
    val id: Long,
    val url: String,
    val nodeId: String,
    val htmlUrl: String,
    val diffUrl: String,
    val patchUrl: String,
    val issueUrl: String,
    val number: Int,
    val state: String,
    val isLocked: Boolean,
    val title: String,
    val user: RepoUser,
    val body: String?,
    val createdAt: String,
    val updatedAt: String,
    val closedAt: String?,
    val mergedAt: String?,
    val mergeCommitSha: String?,
    val assignee: String?,
    val assignees: List<String>,
    val requestedReviewers: List<String>,
    val requestedTeams: List<String>,
    val labels: List<String>,
    val milestone: String?,
    val draft: Boolean,
    val commitsUrl: String,
    val reviewCommentsUrl: String,
    val reviewCommentUrl: String,
    val commentsUrl: String,
    val statusesUrl: String
)