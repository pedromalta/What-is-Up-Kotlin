package whatisup.kotlin.app.data.persistence

import whatisup.kotlin.app.data.persistence.models.RepositoryPersistenceModel
import whatisup.kotlin.app.data.persistence.models.PullRequestPersistenceModel

interface LocalDB {
    fun getRepositories(page: Int): List<RepositoryPersistenceModel>
    fun getPullRequests(repoId: Long): List<PullRequestPersistenceModel>?
    fun addOrUpdateRepositories(repos: List<RepositoryPersistenceModel>)
    fun addOrUpdatePullRequests(repoId: Long, pullRequests: List<PullRequestPersistenceModel>)
}

class RoomDB : LocalDB {
    override fun getRepositories(page: Int): List<RepositoryPersistenceModel> {
        return emptyList()
    }

    override fun getPullRequests(repoId: Long): List<PullRequestPersistenceModel>? {
        return null
    }

    override fun addOrUpdateRepositories(repos: List<RepositoryPersistenceModel>) {

    }

    override fun addOrUpdatePullRequests(repoId: Long, pullRequests: List<PullRequestPersistenceModel>) {

    }

}
