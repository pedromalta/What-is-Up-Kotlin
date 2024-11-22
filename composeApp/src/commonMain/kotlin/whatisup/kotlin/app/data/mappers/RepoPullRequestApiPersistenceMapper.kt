package whatisup.kotlin.app.data.mappers

import whatisup.kotlin.app.data.persistence.models.RepoPullRequest as PersistencePullRequest
import whatisup.kotlin.app.data.api.models.RepoPullRequest as ApiPullRequest

class RepoPullRequestApiPersistenceMapper: Mapper<ApiPullRequest, PersistencePullRequest> {
    override fun transform(origin: ApiPullRequest): PersistencePullRequest {
        origin.apply {
            return PersistencePullRequest(
                id = id,
                title = title,
                body = body,
                state = state,
                createdAt = createdAt,
                updatedAt = updatedAt,
                closedAt = closedAt,
                mergedAt = mergedAt,
                userId = user.id,
                userLogin = user.login,
                userUrl = user.url,
                userNodeId = user.nodeId,
                userAvatarUrl = user.avatarUrl,
                userGravatarId = user.gravatarId,
                userType = user.type,
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
