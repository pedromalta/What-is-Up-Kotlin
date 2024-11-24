package whatisup.kotlin.app.ui.mappers

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDate
import kotlinx.datetime.toLocalDateTime
import whatisup.kotlin.app.data.mappers.api_to_entity_model.Mapper
import whatisup.kotlin.app.domain.models.PullRequestModel as DomainPullRequest
import whatisup.kotlin.app.ui.model.PullRequest as UIPullRequest

/**
 * Mapper from [DomainPullRequest] to [UIPullRequest]
 */
class PullRequestMapper: Mapper<DomainPullRequest, UIPullRequest> {
    override fun transform(origin: DomainPullRequest): UIPullRequest {
        return UIPullRequest(
            id = origin.id,
            title = origin.title,
            userName = origin.user.login,
            body = origin.body ?: "",
            number = origin.number,
            userAvatar = origin.user.avatarUrl,
            createdAt = parseDateTimeStringToLocalDate(origin.createdAt),
            htmlUrl = origin.htmlUrl
        )
    }

    private fun parseDateTimeStringToLocalDate(dateTimeString: String): LocalDateTime {
        // Example of time here "2024-11-13T20:28:05Z"
        // Parse the string as an Instant (since it contains a timezone 'Z')
        val instant = Instant.parse(dateTimeString)

        // Convert the Instant to LocalDate using the system's default zone
        return instant.toLocalDateTime(TimeZone.currentSystemDefault())
    }

}