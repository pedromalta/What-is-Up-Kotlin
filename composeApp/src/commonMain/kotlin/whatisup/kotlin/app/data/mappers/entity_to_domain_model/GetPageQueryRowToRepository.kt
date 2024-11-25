package whatisup.kotlin.app.data.mappers.entity_to_domain_model

import whatisup.kotlin.app.data.db.GetPage
import whatisup.kotlin.app.data.mappers.api_to_entity_model.Mapper
import whatisup.kotlin.app.data.mappers.api_to_entity_model.toBoolean
import whatisup.kotlin.app.domain.models.RepositoryModel
import whatisup.kotlin.app.domain.models.UserModel

/**
 * Map a [GetPage] query response into a [RepositoryModel]
 */
class GetPageQueryRowToRepository : Mapper<GetPage, RepositoryModel> {
    override fun transform(origin: GetPage): RepositoryModel {
        return RepositoryModel(
            id = origin.id,
            name = origin.name,
            fullName = origin.fullName,
            isPrivate = origin.isPrivate.toBoolean(),
            owner = UserModel(
                id = origin.id_,
                login = origin.login,
                url = origin.url_,
                nodeId = origin.nodeId_,
                avatarUrl = origin.avatarUrl,
                gravatarId = origin.gravatarId,
                type = origin.type,
            ),
            htmlUrl = origin.htmlUrl,
            description = origin.description,
            isFork = origin.isFork.toBoolean(),
            url = origin.url,
            homepage = origin.homepage,
            size = origin.size,
            stargazersCount = origin.stargazersCount,
            watchersCount = origin.watchersCount,
            language = origin.language,
            hasIssues = origin.hasIssues.toBoolean(),
            hasProjects = origin.hasProjects.toBoolean(),
            hasDownloads = origin.hasDownloads.toBoolean(),
            hasWiki = origin.hasWiki.toBoolean(),
            hasPages = origin.hasPages.toBoolean(),
            hasDiscussions = origin.hasDiscussions.toBoolean(),
            forksCount = origin.forksCount,
            mirrorUrl = origin.mirrorUrl,
            isArchived = origin.isArchived.toBoolean(),
            isDisabled = origin.isDisabled.toBoolean(),
            openIssuesCount = origin.openIssuesCount,
            topics = origin.topics?.split(",") ?: emptyList(),
            visibility = origin.visibility,
            forks = origin.forks,
            openIssues = origin.openIssues,
            watchers = origin.watchers,
            defaultBranch = origin.defaultBranch,
            score = origin.score,
        )
    }
}