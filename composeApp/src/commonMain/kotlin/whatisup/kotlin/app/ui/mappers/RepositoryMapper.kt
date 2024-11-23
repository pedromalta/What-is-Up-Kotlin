package whatisup.kotlin.app.ui.mappers

import whatisup.kotlin.app.data.mappers.Mapper
import whatisup.kotlin.app.domain.models.RepositoryModel as DomainRepository
import whatisup.kotlin.app.ui.model.Repo as UIRepository

class RepositoryMapper: Mapper<DomainRepository, UIRepository> {
    override fun to(origin: DomainRepository): UIRepository {
        return UIRepository(
            id = origin.id,
            name = origin.name,
            ownerLogin = origin.ownerLogin,
            description = origin.description ?: "",
            forksCount = origin.forksCount,
            starsCount = origin.stargazersCount
        )
    }

}