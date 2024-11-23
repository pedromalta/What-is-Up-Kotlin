package whatisup.kotlin.app.data.mappers.persistence_to_domain_model

import whatisup.kotlin.app.data.mappers.Mapper
import whatisup.kotlin.app.domain.models.PullRequestModel as DomainPullRequest
import whatisup.kotlin.app.data.persistence.models.PullRequestPersistenceModel as PersistencePullRequest

class PullRequestMapper: Mapper<PersistencePullRequest, DomainPullRequest> {
    override fun to(origin: PersistencePullRequest): DomainPullRequest {
        origin.apply {
            return DomainPullRequest(
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
