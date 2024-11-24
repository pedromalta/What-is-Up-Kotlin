package whatisup.kotlin.app.data.mappers.api_to_entity_model

import whatisup.kotlin.app.data.api.models.PullRequestApiModel
import whatisup.kotlin.app.data.db.PullRequestEntity

/**
 * Map a [PullRequestApiModel] into a [PullRequestEntity]
 */
class PullRequestMapper : Mapper<PullRequestApiModel, PullRequestEntity> {
    override fun transform(origin: PullRequestApiModel): PullRequestEntity {
        origin.apply {
            return PullRequestEntity(
                id = id,
                title = title,
                body = body,
                state = state,
                createdAt = createdAt,
                updatedAt = updatedAt,
                closedAt = closedAt,
                mergedAt = mergedAt,
                userId = user.id,
                url = url,
                nodeId = nodeId,
                htmlUrl = htmlUrl,
                diffUrl = diffUrl,
                patchUrl = patchUrl,
                issueUrl = issueUrl,
                number = number,
                isLocked = if (isLocked) 1 else 0,
                mergeCommitSha = mergeCommitSha,
                draft = if (draft) 1 else 0,
                commitsUrl = commitsUrl,
                reviewCommentsUrl = reviewCommentsUrl,
                reviewCommentUrl = reviewCommentUrl,
                commentsUrl = commentsUrl,
                statusesUrl = statusesUrl,
                repoId = repoId,
            )
        }
    }
}
