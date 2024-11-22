package whatisup.kotlin.app.ui.mappers

import whatisup.kotlin.app.data.mappers.Mapper
import whatisup.kotlin.app.domain.models.Repo as RepoDomain
import whatisup.kotlin.app.ui.model.Repo as RepoUI

class RepoUIMapper: Mapper<RepoDomain, RepoUI> {
    override fun transform(origin: RepoDomain): RepoUI {
        return RepoUI(
            id = origin.id,
            name = origin.name,
            fullName = origin.fullName,
        )
    }

}