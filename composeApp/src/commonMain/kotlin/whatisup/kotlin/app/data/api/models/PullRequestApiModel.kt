package whatisup.kotlin.app.data.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PullRequestApiModel(
    val id: Long,
    val url: String,
    @SerialName("node_id") val nodeId: String,
    @SerialName("html_url") val htmlUrl: String,
    @SerialName("diff_url") val diffUrl: String,
    @SerialName("patch_url") val patchUrl: String,
    @SerialName("issue_url") val issueUrl: String,
    val number: Int,
    val state: String,
    @SerialName("locked") val isLocked: Boolean,
    val title: String,
    val user: UserApiModel,
    val body: String?,
    @SerialName("created_at") val createdAt: String,
    @SerialName("updated_at") val updatedAt: String,
    @SerialName("closed_at") val closedAt: String?,
    @SerialName("merged_at") val mergedAt: String?,
    @SerialName("merge_commit_sha") val mergeCommitSha: String?,
    val draft: Boolean,
    @SerialName("commits_url") val commitsUrl: String,
    @SerialName("review_comments_url") val reviewCommentsUrl: String,
    @SerialName("review_comment_url") val reviewCommentUrl: String,
    @SerialName("comments_url") val commentsUrl: String,
    @SerialName("statuses_url") val statusesUrl: String
)
