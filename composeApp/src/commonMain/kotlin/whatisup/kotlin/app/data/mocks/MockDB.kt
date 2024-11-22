package whatisup.kotlin.app.data.mocks

import whatisup.kotlin.app.data.persistence.LocalDB
import whatisup.kotlin.app.data.persistence.models.Repo
import whatisup.kotlin.app.data.persistence.models.RepoPullRequest

class MockDB: LocalDB {

    private val repoSet = mutableSetOf<Repo>()

    override fun getRepos(page: Int): List<Repo> {
        return repoSet.toList()
    }

    override fun getPullRequests(repoId: Long): RepoPullRequest? {
        return null
    }

    override fun addOrUpdateRepos(repos: List<Repo>) {
        repoSet.addAll(repos)
    }
}