package whatisup.kotlin.app.data.mappers

import whatisup.kotlin.app.data.persistence.models.Repo as PersistenceRepo
import whatisup.kotlin.app.domain.models.Repo as ModelRepo

class RepoPersistenceModelMapper: Mapper<PersistenceRepo, ModelRepo> {

    override fun transform(origin: PersistenceRepo): ModelRepo {
        origin.apply {
            return ModelRepo(
                id = id,
                name = name,
                fullName = fullName,
                isPrivate = isPrivate,
                ownerId = ownerId,
                ownerLogin = ownerLogin,
                ownerUrl = ownerUrl,
                ownerNodeId = ownerNodeId,
                ownerAvatarUrl = ownerAvatarUrl,
                ownerGravatarId = ownerGravatarId,
                ownerType = ownerType,
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
