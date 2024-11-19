package whatisup.kotlin.app.data.mappers

import whatisup.kotlin.app.data.persistence.models.RepoPullRequest as PersistencePullRequest
import whatisup.kotlin.app.data.api.models.RepoPullRequest as ApiPullRequest

class RepoPullRequestMapper(
    private val repoUserMapper: RepoUserMapper = RepoUserMapper()
): Mapper<ApiPullRequest, PersistencePullRequest> {
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
                user = repoUserMapper.transform(user),
                url = url,
                nodeId = nodeId,
                htmlUrl = htmlUrl,
                diffUrl = diffUrl,
                patchUrl = patchUrl,
                issueUrl = issueUrl,
                number = number,
                isLocked = isLocked,
                mergeCommitSha = mergeCommitSha,
                assignee = assignee,
                assignees = assignees,
                requestedReviewers = requestedReviewers,
                requestedTeams = requestedTeams,
                labels = labels,
                milestone = milestone,
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
