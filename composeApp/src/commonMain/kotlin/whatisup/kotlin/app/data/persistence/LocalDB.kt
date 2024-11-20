package whatisup.kotlin.app.data.persistence

import whatisup.kotlin.app.data.persistence.models.Repo
import whatisup.kotlin.app.data.persistence.models.RepoPullRequest

interface LocalDB {
    fun getRepos(page: Int): List<Repo>
    fun getPullRequests(repoId: Long): RepoPullRequest?
    fun addOrUpdateRepos(repos: List<Repo>)
}

class RoomDB : LocalDB {
    override fun getRepos(page: Int): List<Repo> {
        return emptyList()
    }

    override fun getPullRequests(repoId: Long): RepoPullRequest? {
        return null
    }

    override fun addOrUpdateRepos(repos: List<Repo>) {

    }

}