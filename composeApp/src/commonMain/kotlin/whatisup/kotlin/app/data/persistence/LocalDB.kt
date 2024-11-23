package whatisup.kotlin.app.data.persistence

import whatisup.kotlin.app.data.api.models.PullRequestApiModel
import whatisup.kotlin.app.data.api.models.RepositoryApiModel
import whatisup.kotlin.app.domain.models.PullRequestModel
import whatisup.kotlin.app.domain.models.RepositoryModel

interface LocalDB {
    fun getRepositories(page: Int): List<RepositoryModel>
    fun getPullRequests(repoId: Long): List<PullRequestModel>
    fun addOrUpdateRepositories(repositories: List<RepositoryApiModel>)
    fun addOrUpdatePullRequests(pullRequests: List<PullRequestApiModel>)
}
