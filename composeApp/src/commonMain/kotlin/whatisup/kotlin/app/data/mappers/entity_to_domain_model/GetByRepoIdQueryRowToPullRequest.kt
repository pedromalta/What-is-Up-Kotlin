package whatisup.kotlin.app.data.mappers.entity_to_domain_model

import whatisup.kotlin.app.data.db.GetByRepoId
import whatisup.kotlin.app.data.mappers.api_to_entity_model.Mapper
import whatisup.kotlin.app.data.mappers.api_to_entity_model.toBoolean
import whatisup.kotlin.app.domain.models.PullRequestModel
import whatisup.kotlin.app.domain.models.UserModel

class GetByRepoIdQueryRowToPullRequest: Mapper<GetByRepoId, PullRequestModel> {
    override fun transform(origin: GetByRepoId): PullRequestModel {
        return PullRequestModel(
            id = origin.id,
            url = origin.url,
            repoId = origin.repoId,
            nodeId = origin.nodeId,
            htmlUrl = origin.htmlUrl,
            diffUrl = origin.diffUrl,
            patchUrl = origin.patchUrl,
            issueUrl = origin.issueUrl,
            number = origin.number,
            state = origin.state,
            isLocked = origin.isLocked.toBoolean(),
            title = origin.title,
            body = origin.body,
            createdAt = origin.createdAt,
            updatedAt = origin.updatedAt,
            closedAt = origin.closedAt,
            mergedAt = origin.mergedAt,
            mergeCommitSha = origin.mergeCommitSha,
            draft = origin.draft.toBoolean(),
            commitsUrl = origin.commitsUrl,
            reviewCommentsUrl = origin.reviewCommentsUrl,
            reviewCommentUrl = origin.reviewCommentUrl,
            commentsUrl = origin.commentsUrl,
            statusesUrl = origin.statusesUrl,
            user = UserModel(
                id = origin.id_,
                login = origin.login,
                url = origin.url_,
                nodeId = origin.nodeId_,
                avatarUrl = origin.avatarUrl,
                gravatarId = origin.gravatarId,
                type = origin.type,
            )
        )
    }
}