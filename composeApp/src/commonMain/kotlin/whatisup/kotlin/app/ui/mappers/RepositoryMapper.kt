package whatisup.kotlin.app.ui.mappers

import whatisup.kotlin.app.data.mappers.api_to_entity_model.Mapper
import whatisup.kotlin.app.domain.models.RepositoryModel as DomainRepository
import whatisup.kotlin.app.ui.model.Repository as UIRepository

/**
 * Mapper from [DomainRepository] to [UIRepository]
 */
class RepositoryMapper : Mapper<DomainRepository, UIRepository> {
    override fun transform(origin: DomainRepository): UIRepository {
        return UIRepository(
            id = origin.id,
            name = origin.name,
            ownerLogin = origin.owner.login,
            description = origin.description ?: "",
            forksCount = origin.forksCount,
            starsCount = origin.stargazersCount,
            ownerAvatar = origin.owner.avatarUrl,
            ownerGravatar = origin.owner.gravatarId,
        )
    }

}