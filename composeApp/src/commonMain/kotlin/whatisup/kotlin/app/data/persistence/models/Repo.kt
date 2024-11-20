package whatisup.kotlin.app.data.persistence.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Repo(
    @PrimaryKey val id: Long,
    val page: Int,
    val name: String,
    val fullName: String,
    val isPrivate: Boolean,
    val ownerId: Long,
    val ownerLogin: String,
    val ownerUrl: String,
    val ownerNodeId: String,
    val ownerAvatarUrl: String,
    val ownerGravatarId: String?,
    val ownerType: String,
    val htmlUrl: String,
    val description: String?,
    val isFork: Boolean,
    val url: String,
    val homepage: String?,
    val size: Int,
    val stargazersCount: Int,
    val watchersCount: Int,
    val language: String?,
    val hasIssues: Boolean,
    val hasProjects: Boolean,
    val hasDownloads: Boolean,
    val hasWiki: Boolean,
    val hasPages: Boolean,
    val hasDiscussions: Boolean,
    val forksCount: Int,
    val mirrorUrl: String?,
    val isArchived: Boolean,
    val isDisabled: Boolean,
    val openIssuesCount: Int,
    val topics: List<String> = emptyList(),
    val visibility: String,
    val forks: Int,
    val openIssues: Int,
    val watchers: Int,
    val defaultBranch: String,
    val score: Double
)
