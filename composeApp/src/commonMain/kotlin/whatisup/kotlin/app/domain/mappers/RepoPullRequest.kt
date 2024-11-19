package whatisup.kotlin.app.domain.mappers

import whatisup.kotlin.app.domain.persistence.models.RepoPullRequest as PersistencePullRequest
import whatisup.kotlin.app.domain.api.models.RepoPullRequest as ApiPullRequest

fun ApiPullRequest.toPersistencePullRequest() = PersistencePullRequest(
    id = id,
    title = title,
    body = body,
    state = state,
    createdAt = createdAt,
    updatedAt = updatedAt,
    closedAt = closedAt,
    mergedAt = mergedAt,
    user = user.toPersistenceRepoUser(),
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