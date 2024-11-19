package whatisup.kotlin.app.data.mappers

import whatisup.kotlin.app.data.persistence.models.RepoUser as PersistenceRepoUser
import whatisup.kotlin.app.data.api.models.RepoUser as ApiRepoUser

class RepoUserMapper : Mapper<ApiRepoUser, PersistenceRepoUser> {
    override fun transform(origin: ApiRepoUser): PersistenceRepoUser {
        origin.apply {
            return PersistenceRepoUser(
                id = id,
                login = login,
                url = url,
                nodeId = nodeId,
                avatarUrl = avatarUrl,
                gravatarId = gravatarId,
                type = type
            )
        }
    }
}
