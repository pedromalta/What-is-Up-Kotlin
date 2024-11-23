package whatisup.kotlin.app.domain.models

data class UserModel(
    val id: Long,
    val login: String,
    val url: String,
    val nodeId: String,
    val avatarUrl: String,
    val gravatarId: String?,
    val type: String,
)
