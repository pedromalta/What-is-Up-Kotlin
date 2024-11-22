package whatisup.kotlin.app.ui.mappers

import whatisup.kotlin.app.data.mappers.Mapper
import whatisup.kotlin.app.domain.models.RepoPullRequest as RepoPullRequestDomain
import whatisup.kotlin.app.ui.model.PullRequest as RepoPullRequestsUI

class RepoPullRequestMapper: Mapper<RepoPullRequestDomain, RepoPullRequestsUI> {
    override fun transform(origin: RepoPullRequestDomain): RepoPullRequestsUI {
        return RepoPullRequestsUI(
            id = origin.id,
            title = origin.title,
            userName = origin.userLogin,
            body = origin.body ?: "",
        )
    }

}