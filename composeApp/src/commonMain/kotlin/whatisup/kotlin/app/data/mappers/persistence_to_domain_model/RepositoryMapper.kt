package whatisup.kotlin.app.data.mappers.persistence_to_domain_model

import whatisup.kotlin.app.data.mappers.Mapper
import whatisup.kotlin.app.domain.models.RepositoryModel as DomainRepository
import whatisup.kotlin.app.data.persistence.models.RepositoryPersistenceModel as PersistenceRepository

class RepositoryMapper: Mapper<PersistenceRepository, DomainRepository> {

    override fun to(origin: PersistenceRepository): DomainRepository {
        origin.apply {
            return DomainRepository(
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
