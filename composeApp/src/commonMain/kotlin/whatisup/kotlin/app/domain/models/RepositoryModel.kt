package whatisup.kotlin.app.domain.models

/**
 * Data model for a Repositoryß
 */
data class RepositoryModel(
    val id: Long,
    val name: String,
    val fullName: String,
    val isPrivate: Boolean,
    val owner: UserModel,
    val htmlUrl: String,
    val description: String?,
    val isFork: Boolean,
    val url: String,
    val homepage: String?,
    val size: Long,
    val stargazersCount: Long,
    val watchersCount: Long,
    val language: String?,
    val hasIssues: Boolean,
    val hasProjects: Boolean,
    val hasDownloads: Boolean,
    val hasWiki: Boolean,
    val hasPages: Boolean,
    val hasDiscussions: Boolean,
    val forksCount: Long,
    val mirrorUrl: String?,
    val isArchived: Boolean,
    val isDisabled: Boolean,
    val openIssuesCount: Long,
    val topics: List<String> = emptyList(),
    val visibility: String,
    val forks: Long,
    val openIssues: Long,
    val watchers: Long,
    val defaultBranch: String,
    val score: Double
)
