package whatisup.kotlin.app.ui.model

data class PullRequest(
    override val id: Long,
    val title: String,
    val userName: String,
    val body: String,
): StableId
