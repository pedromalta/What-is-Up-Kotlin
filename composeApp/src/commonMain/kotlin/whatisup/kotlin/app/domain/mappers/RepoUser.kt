package whatisup.kotlin.app.domain.mappers

import whatisup.kotlin.app.domain.persistence.models.RepoUser as PersistenceRepoUser
import whatisup.kotlin.app.domain.api.models.RepoUser as ApiRepoUser

fun ApiRepoUser.toPersistenceRepoUser() = PersistenceRepoUser(
    id = id,
    login = login,
    url = url,
    nodeId = nodeId,
    avatarUrl = avatarUrl,
    gravatarId = gravatarId,
    type = type
)