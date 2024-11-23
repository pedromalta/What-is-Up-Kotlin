package whatisup.kotlin.app.data.mappers.api_to_entity_model

import whatisup.kotlin.app.data.api.models.RepositoryApiModel
import whatisup.kotlin.app.data.db.RepositoryEntity

class RepositoryMapper :
    Mapper<RepositoryApiModel, RepositoryEntity> {

    override fun transform(origin: RepositoryApiModel): RepositoryEntity {
        origin.apply {
            return RepositoryEntity(
                id = id,
                name = name,
                fullName = fullName,
                isPrivate = if (isPrivate) 1 else 0,
                ownerId = owner.id,
                htmlUrl = htmlUrl,
                description = description,
                isFork = if (isFork) 1 else 0,
                url = url,
                homepage = homepage,
                size = size.toLong(),
                stargazersCount = stargazersCount.toLong(),
                watchersCount = watchersCount.toLong(),
                language = language,
                hasIssues = if (hasIssues) 1 else 0,
                hasProjects = if (hasProjects) 1 else 0,
                hasDownloads = if (hasDownloads) 1 else 0,
                hasWiki = if (hasWiki) 1 else 0,
                hasPages = if (hasPages) 1 else 0,
                hasDiscussions = if (hasDiscussions) 1 else 0,
                forksCount = forksCount.toLong(),
                mirrorUrl = mirrorUrl,
                isArchived = if (isArchived) 1 else 0,
                isDisabled = if (isDisabled) 1 else 0,
                openIssuesCount = openIssuesCount.toLong(),
                visibility = visibility,
                forks = forks.toLong(),
                openIssues = openIssues.toLong(),
                watchers = watchers.toLong(),
                defaultBranch = defaultBranch,
                score = score,
                nodeId = nodeId,
                topics = topics.joinToString(separator = ",")
            )
        }
    }
}
