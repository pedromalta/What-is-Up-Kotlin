package whatisup.kotlin.app.data.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RepositoryListApiModel(
    @SerialName("total_count")
    val totalCount: Int,
    @SerialName("incomplete_results")
    val isIncompleteResults: Boolean,
    val items: List<RepositoryApiModel>
)