package whatisup.kotlin.app.data.models

data class RepoUser(
    val id: Long,
    val login: String,
    val url: String,
    val nodeId: String,
    val avatarUrl: String,
    val gravatarId: String?,
    val type: String,
)
