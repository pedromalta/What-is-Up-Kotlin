package whatisup.kotlin.app.data.persistence

import whatisup.kotlin.app.data.api.models.PullRequestApiModel
import whatisup.kotlin.app.data.api.models.RepositoryApiModel
import whatisup.kotlin.app.domain.models.PullRequestModel
import whatisup.kotlin.app.domain.models.RepositoryModel
import whatisup.kotlin.app.domain.models.UserModel

/**
 * Mock Implementation of [LocalDB]
 */
class MockDB : LocalDB {
    private val repositoriesSet = mutableSetOf<RepositoryModel>()
    private val pullRequestsSet: HashMap<Long, List<PullRequestModel>?> = hashMapOf()

    override fun getRepositories(page: Int): List<RepositoryModel> {
        return repositoriesSet.toList()
    }

    override fun getPullRequests(repoId: Long): List<PullRequestModel> {
        return pullRequestsSet[repoId] ?: emptyList()
    }

    override fun addOrUpdateRepositories(repositories: List<RepositoryApiModel>) {
        repositoriesSet.addAll(
            repositories.map {
                RepositoryModel(
                    id = it.id,
                    name = it.name,
                    fullName = it.fullName,
                    isPrivate = it.isPrivate,
                    owner = UserModel(
                        id = it.owner.id,
                        login = it.owner.login,
                        url = it.owner.url,
                        nodeId = it.owner.nodeId,
                        avatarUrl = it.owner.avatarUrl,
                        gravatarId = it.owner.gravatarId,
                        type = it.owner.type,
                    ),
                    htmlUrl = it.htmlUrl,
                    description = it.description,
                    isFork = it.isFork,
                    homepage = it.homepage,
                    size = it.size.toLong(),
                    stargazersCount = it.stargazersCount.toLong(),
                    watchersCount = it.watchersCount.toLong(),
                    language = it.language,
                    hasIssues = it.hasIssues,
                    hasProjects = it.hasProjects,
                    hasDownloads = it.hasDownloads,
                    hasWiki = it.hasWiki,
                    hasPages = it.hasPages,
                    hasDiscussions = it.hasDiscussions,
                    forksCount = it.forksCount.toLong(),
                    mirrorUrl = it.mirrorUrl,
                    isArchived = it.isArchived,
                    isDisabled = it.isDisabled,
                    openIssuesCount = it.openIssuesCount.toLong(),
                    topics = it.topics,
                    visibility = it.visibility,
                    forks = it.forks.toLong(),
                    openIssues = it.openIssues.toLong(),
                    watchers = it.watchers.toLong(),
                    defaultBranch = it.defaultBranch,
                    score = it.score,
                    url = it.url,
                )
            }
        )
    }

    override fun addOrUpdatePullRequests(pullRequests: List<PullRequestApiModel>) {
        var repoId: Long? = null
        val pullRequestsList = pullRequests.map {
            repoId = it.repoId
            PullRequestModel(
                id = it.id,
                url = it.url,
                repoId = it.repoId,
                nodeId = it.nodeId,
                htmlUrl = it.htmlUrl,
                diffUrl = it.diffUrl,
                patchUrl = it.patchUrl,
                issueUrl = it.issueUrl,
                number = it.number,
                state = it.state,
                isLocked = it.isLocked,
                title = it.title,
                body = it.body,
                createdAt = it.createdAt,
                updatedAt = it.updatedAt,
                closedAt = it.closedAt,
                mergedAt = it.mergedAt,
                mergeCommitSha = it.mergeCommitSha,
                user = UserModel(
                    id = it.user.id,
                    login = it.user.login,
                    url = it.user.url,
                    nodeId = it.user.nodeId,
                    avatarUrl = it.user.avatarUrl,
                    gravatarId = it.user.gravatarId,
                    type = it.user.type,
                ),
                draft = it.draft,
                commitsUrl = it.commitsUrl,
                reviewCommentsUrl = it.reviewCommentsUrl,
                reviewCommentUrl = it.reviewCommentUrl,
                commentsUrl = it.commentsUrl,
                statusesUrl = it.statusesUrl,
            )
        }
        repoId?.let {
            pullRequestsSet[it] = pullRequestsList
        }

    }
}
