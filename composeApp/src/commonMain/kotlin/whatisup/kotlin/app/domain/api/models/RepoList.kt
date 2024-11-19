package whatisup.kotlin.app.domain.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RepoList(
    @SerialName("total_count")
    val totalCount: Int,
    @SerialName("incomplete_results")
    val isIncompleteResults: Boolean,
    val items: List<Repo>

)