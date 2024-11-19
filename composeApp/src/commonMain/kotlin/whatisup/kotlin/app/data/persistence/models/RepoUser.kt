package whatisup.kotlin.app.data.persistence.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RepoUser(
    @PrimaryKey val id: Long,
    val login: String,
    val url: String,
    val nodeId: String,
    val avatarUrl: String,
    val gravatarId: String?,
    val type: String,
)
