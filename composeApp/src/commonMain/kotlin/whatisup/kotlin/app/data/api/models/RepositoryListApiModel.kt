package whatisup.kotlin.app.data.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Wrapper object that encapsulates a list of [RepositoryApiModel] from GitHub API
 */
@Serializable
data class RepositoryListApiModel(
    @SerialName("total_count")
    val totalCount: Int,
    @SerialName("incomplete_results")
    val isIncompleteResults: Boolean,
    val items: List<RepositoryApiModel>
)