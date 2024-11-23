package whatisup.kotlin.app.data.mappers.api_to_persistence_model

import whatisup.kotlin.app.data.mappers.Mapper
import whatisup.kotlin.app.data.api.models.UserApiModel as ApiUser
import whatisup.kotlin.app.data.persistence.models.UserPersistenceModel as PersistenceUser

class UserMapper(private val page: Int) :
    Mapper<ApiUser, PersistenceUser> {

    override fun to(origin: ApiUser): PersistenceUser {
        origin.apply {
            return PersistenceUser(
                id = id,
                login = login,
                url = url,
                nodeId = nodeId,
                avatarUrl = avatarUrl,
                gravatarId = gravatarId,
                type = type,
            )
        }
    }
}