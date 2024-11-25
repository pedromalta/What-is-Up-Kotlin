package whatisup.kotlin.app.ui.model

/**
 * UI Data that represents a Repository
 * [equals] and [hashCode] are based only on the [StableId]
 */
data class Repository(
    override val id: Long,
    val name: String,
    val ownerLogin: String,
    val ownerAvatar: String,
    val ownerGravatar: String?,
    val description: String,
    val forksCount: Long,
    val starsCount: Long,
) : StableId {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as Repository

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}