package whatisup.kotlin.app.data.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Repo(
    val id: Long,
    @SerialName("node_id") val nodeId: String,
    val name: String,
    @SerialName("full_name") val fullName: String,
    val isPrivate: Boolean,
    val owner: RepoUser,
    @SerialName("html_url") val htmlUrl: String,
    val description: String?,
    @SerialName("fork")
    val isFork: Boolean,
    val url: String,
    val homepage: String?,
    val size: Int,
    @SerialName("stargazers_count") val stargazersCount: Int,
    @SerialName("watchers_count") val watchersCount: Int,
    val language: String?,
    @SerialName("has_issues") val hasIssues: Boolean,
    @SerialName("has_projects") val hasProjects: Boolean,
    @SerialName("has_downloads") val hasDownloads: Boolean,
    @SerialName("has_wiki") val hasWiki: Boolean,
    @SerialName("has_pages") val hasPages: Boolean,
    @SerialName("has_discussions") val hasDiscussions: Boolean,
    @SerialName("forks_count") val forksCount: Int,
    @SerialName("mirror_url") val mirrorUrl: String?,
    @SerialName("archived") val isArchived: Boolean,
    @SerialName("disabled") val isDisabled: Boolean,
    @SerialName("open_issues_count") val openIssuesCount: Int,
    val topics: List<String> = emptyList(),
    val visibility: String,
    val forks: Int,
    @SerialName("open_issues") val openIssues: Int,
    val watchers: Int,
    @SerialName("default_branch") val defaultBranch: String,
    val score: Double
)