package whatisup.kotlin.app.domain.models

data class PullRequestModel(
    val id: Long,
    val url: String,
    val nodeId: String,
    val htmlUrl: String,
    val diffUrl: String,
    val patchUrl: String,
    val issueUrl: String,
    val number: Long,
    val state: String,
    val isLocked: Boolean,
    val title: String,
    val user: UserModel,
    val repoId: Long,
    val body: String?,
    val createdAt: String,
    val updatedAt: String,
    val closedAt: String?,
    val mergedAt: String?,
    val mergeCommitSha: String?,
    val draft: Boolean,
    val commitsUrl: String,
    val reviewCommentsUrl: String,
    val reviewCommentUrl: String,
    val commentsUrl: String,
    val statusesUrl: String
)