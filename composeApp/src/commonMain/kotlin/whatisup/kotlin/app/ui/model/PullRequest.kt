package whatisup.kotlin.app.ui.model

import kotlinx.datetime.LocalDateTime

/**
 * UI Data that represents a Pull Request
 * [equals] and [hashCode] are based only on the [StableId]
 */
data class PullRequest(
    override val id: Long,
    val title: String,
    val userName: String,
    val body: String,
    val number: Long,
    val userAvatar: String,
    val createdAt: LocalDateTime,
): StableId {

    val createAtDate: String
        get() {
            return "${createdAt.year}/${createdAt.monthNumber}/${createdAt.dayOfMonth}"
        }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as PullRequest



        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}
