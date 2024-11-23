package whatisup.kotlin.app.ui.mappers

import whatisup.kotlin.app.data.mappers.Mapper
import whatisup.kotlin.app.domain.models.PullRequestModel as DomainPullRequest
import whatisup.kotlin.app.ui.model.PullRequest as UIPullRequest

class PullRequestMapper: Mapper<DomainPullRequest, UIPullRequest> {
    override fun to(origin: DomainPullRequest): UIPullRequest {
        return UIPullRequest(
            id = origin.id,
            title = origin.title,
            userName = origin.userLogin,
            body = origin.body ?: "",
        )
    }

}