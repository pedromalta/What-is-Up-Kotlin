package whatisup.kotlin.app.data.mappers

import whatisup.kotlin.app.domain.models.RepoPullRequest as ModelPullRequest
import whatisup.kotlin.app.data.persistence.models.RepoPullRequest as PersistencePullRequest

class RepoPullRequestPersistenceModelMapper: Mapper<PersistencePullRequest, ModelPullRequest> {
    override fun transform(origin: PersistencePullRequest): ModelPullRequest {
        origin.apply {
            return ModelPullRequest(
                id = id,
                title = title,
                body = body,
                state = state,
                createdAt = createdAt,
                updatedAt = updatedAt,
                closedAt = closedAt,
                mergedAt = mergedAt,
                userId = userId,
                userLogin = userLogin,
                userUrl = userUrl,
                userNodeId = userNodeId,
                userAvatarUrl = userAvatarUrl,
                userGravatarId = userGravatarId,
                userType = userType,
                url = url,
                nodeId = nodeId,
                htmlUrl = htmlUrl,
                diffUrl = diffUrl,
                patchUrl = patchUrl,
                issueUrl = issueUrl,
                number = number,
                isLocked = isLocked,
                mergeCommitSha = mergeCommitSha,
                draft = draft,
                commitsUrl = commitsUrl,
                reviewCommentsUrl = reviewCommentsUrl,
                reviewCommentUrl = reviewCommentUrl,
                commentsUrl = commentsUrl,
                statusesUrl = statusesUrl,
            )
        }
    }
}
