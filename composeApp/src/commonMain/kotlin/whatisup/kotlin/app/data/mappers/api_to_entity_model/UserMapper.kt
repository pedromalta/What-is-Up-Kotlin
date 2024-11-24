package whatisup.kotlin.app.data.mappers.api_to_entity_model

import whatisup.kotlin.app.data.api.models.UserApiModel
import whatisup.kotlin.app.data.db.UserEntity

/**
 * Map a [UserApiModel] into a [UserEntity]
 */
class UserMapper : Mapper<UserApiModel, UserEntity> {

    override fun transform(origin: UserApiModel): UserEntity {
        origin.apply {
            return UserEntity(
                id = id,
                login = login,
                url = url,
                nodeId = nodeId,
                avatarUrl = avatarUrl,
                gravatarId = gravatarId,
                type = type,
                htmlUrl = htmlUrl,
                followersUrl = followersUrl,
                followingUrl = followingUrl,
                gistsUrl = gistsUrl,
                starredUrl = starredUrl,
                subscriptionsUrl = subscriptionsUrl,
                organizationsUrl = organizationsUrl,
                reposUrl = reposUrl,
                eventsUrl = eventsUrl,
                receivedEventsUrl = receivedEventsUrl,
                userViewType = userViewType,
                siteAdmin = if (siteAdmin) 1 else 0,
            )
        }
    }
}