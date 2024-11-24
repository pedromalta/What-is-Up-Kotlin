package whatisup.kotlin.app.data.persistence

import whatisup.kotlin.app.data.api.models.PullRequestApiModel
import whatisup.kotlin.app.data.api.models.RepositoryApiModel
import whatisup.kotlin.app.domain.models.PullRequestModel
import whatisup.kotlin.app.domain.models.RepositoryModel

/**
 * DB storing Repositories and PullRequests
 */
interface LocalDB {

    /**
     * Get a page of Repositories from DB
     */
    fun getRepositories(page: Int): List<RepositoryModel>

    /**
     * Get Pull Requests for a Repository
     */
    fun getPullRequests(repoId: Long): List<PullRequestModel>

    /**
     * Add or update Repositories on DB
     */
    fun addOrUpdateRepositories(repositories: List<RepositoryApiModel>)

    /**
     * Add or update Pull Requests on DB
     */
    fun addOrUpdatePullRequests(pullRequests: List<PullRequestApiModel>)
}
