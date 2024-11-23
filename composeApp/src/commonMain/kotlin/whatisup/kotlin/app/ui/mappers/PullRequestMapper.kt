package whatisup.kotlin.app.ui.mappers

import whatisup.kotlin.app.data.mappers.api_to_entity_model.Mapper
import whatisup.kotlin.app.domain.models.PullRequestModel as DomainPullRequest
import whatisup.kotlin.app.ui.model.PullRequest as UIPullRequest

class PullRequestMapper: Mapper<DomainPullRequest, UIPullRequest> {
    override fun transform(origin: DomainPullRequest): UIPullRequest {
        return UIPullRequest(
            id = origin.id,
            title = origin.title,
            userName = origin.user.login,
            body = origin.body ?: "",
        )
    }

}