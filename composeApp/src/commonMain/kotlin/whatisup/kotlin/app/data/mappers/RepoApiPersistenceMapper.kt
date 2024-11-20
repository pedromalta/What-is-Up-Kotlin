package whatisup.kotlin.app.data.mappers

import whatisup.kotlin.app.data.api.models.Repo as ApiRepo
import whatisup.kotlin.app.data.persistence.models.Repo as PersistenceRepo

class RepoApiPersistenceMapper(private val page: Int) : Mapper<ApiRepo, PersistenceRepo> {

    override fun transform(origin: ApiRepo): PersistenceRepo {
        origin.apply {
            return PersistenceRepo(
                id = id,
                page = page,
                name = name,
                fullName = fullName,
                isPrivate = isPrivate,
                ownerId = owner.id,
                ownerLogin = owner.login,
                ownerUrl = owner.url,
                ownerNodeId = owner.nodeId,
                ownerAvatarUrl = owner.avatarUrl,
                ownerGravatarId = owner.gravatarId,
                ownerType = owner.type,
                htmlUrl = htmlUrl,
                description = description,
                isFork = isFork,
                url = url,
                homepage = homepage,
                size = size,
                stargazersCount = stargazersCount,
                watchersCount = watchersCount,
                language = language,
                hasIssues = hasIssues,
                hasProjects = hasProjects,
                hasDownloads = hasDownloads,
                hasWiki = hasWiki,
                hasPages = hasPages,
                hasDiscussions = hasDiscussions,
                forksCount = forksCount,
                mirrorUrl = mirrorUrl,
                isArchived = isArchived,
                isDisabled = isDisabled,
                openIssuesCount = openIssuesCount,
                topics = topics,
                visibility = visibility,
                forks = forks,
                openIssues = openIssues,
                watchers = watchers,
                defaultBranch = defaultBranch,
                score = score
            )
        }
    }
}
