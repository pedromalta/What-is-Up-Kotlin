package whatisup.kotlin.app.data.persistence

import whatisup.kotlin.app.data.persistence.models.Repo
import whatisup.kotlin.app.data.persistence.models.RepoPullRequest

interface LocalDB {
    fun getRepos(page: Int): List<Repo>
    fun getRepoPullRequests(repoId: Long): List<RepoPullRequest>?
    fun addOrUpdateRepos(repos: List<Repo>)
    fun addOrUpdateRepoPullRequests(repoId: Long, pullRequests: List<RepoPullRequest>)
}

class RoomDB : LocalDB {
    override fun getRepos(page: Int): List<Repo> {
        return emptyList()
    }

    override fun getRepoPullRequests(repoId: Long): List<RepoPullRequest>? {
        return null
    }

    override fun addOrUpdateRepos(repos: List<Repo>) {

    }

    override fun addOrUpdateRepoPullRequests(repoId: Long, pullRequests: List<RepoPullRequest>) {

    }

}
