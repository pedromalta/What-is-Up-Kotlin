package whatisup.kotlin.app.data.mocks

import whatisup.kotlin.app.data.persistence.LocalDB
import whatisup.kotlin.app.data.persistence.models.Repo
import whatisup.kotlin.app.data.persistence.models.RepoPullRequest

class MockDB: LocalDB {

    private val repoSet = mutableSetOf<Repo>()
    private val repoPullRequestSet: HashMap<Long, List<RepoPullRequest>?> = hashMapOf()

    override fun getRepos(page: Int): List<Repo> {
        return repoSet.toList()
    }

    override fun getRepoPullRequests(repoId: Long):List<RepoPullRequest>? {
        return repoPullRequestSet[repoId]
    }

    override fun addOrUpdateRepos(repos: List<Repo>) {
        repoSet.addAll(repos)
    }

    override fun addOrUpdateRepoPullRequests(repoId: Long, pullRequests: List<RepoPullRequest>) {
        repoPullRequestSet[repoId] = pullRequests

    }
}