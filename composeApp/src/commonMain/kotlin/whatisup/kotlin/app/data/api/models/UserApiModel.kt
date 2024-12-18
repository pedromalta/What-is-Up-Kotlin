package whatisup.kotlin.app.data.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Represents a User from GitHub API
 */
@Serializable
data class UserApiModel(
    val id: Long,
    val login: String,
    val url: String,
    @SerialName("node_id") val nodeId: String,
    @SerialName("avatar_url") val avatarUrl: String,
    @SerialName("gravatar_id") val gravatarId: String?,
    @SerialName("html_url") val htmlUrl: String,
    @SerialName("followers_url") val followersUrl: String,
    @SerialName("following_url") val followingUrl: String,
    @SerialName("gists_url") val gistsUrl: String,
    @SerialName("starred_url") val starredUrl: String,
    @SerialName("subscriptions_url") val subscriptionsUrl: String,
    @SerialName("organizations_url") val organizationsUrl: String,
    @SerialName("repos_url") val reposUrl: String,
    @SerialName("events_url") val eventsUrl: String,
    @SerialName("received_events_url") val receivedEventsUrl: String,
    val type: String,
    @SerialName("user_view_type") val userViewType: String,
    @SerialName("site_admin") val siteAdmin: Boolean
)