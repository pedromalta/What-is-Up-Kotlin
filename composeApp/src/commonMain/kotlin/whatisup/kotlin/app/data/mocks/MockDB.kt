package whatisup.kotlin.app.data.mocks

import whatisup.kotlin.app.data.persistence.LocalDB
import whatisup.kotlin.app.data.persistence.models.RepositoryPersistenceModel
import whatisup.kotlin.app.data.persistence.models.PullRequestPersistenceModel

class MockDB: LocalDB {

    private val repoSet = mutableSetOf<RepositoryPersistenceModel>()
    private val repoPullRequestSet: HashMap<Long, List<PullRequestPersistenceModel>?> = hashMapOf()

    override fun getRepositories(page: Int): List<RepositoryPersistenceModel> {
        return repoSet.toList()
    }

    override fun getPullRequests(repoId: Long):List<PullRequestPersistenceModel>? {
        return repoPullRequestSet[repoId]
    }

    override fun addOrUpdateRepositories(repos: List<RepositoryPersistenceModel>) {
        repoSet.addAll(repos)
    }

    override fun addOrUpdatePullRequests(repoId: Long, pullRequests: List<PullRequestPersistenceModel>) {
        repoPullRequestSet[repoId] = pullRequests

    }
}