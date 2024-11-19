package whatisup.kotlin.app.data.persistence

import whatisup.kotlin.app.data.persistence.models.Repo
import whatisup.kotlin.app.data.persistence.models.RepoPullRequest

interface LocalDB {
    fun getRepos(): List<Repo>
    fun getPullRequests(repoId: Long): RepoPullRequest
    fun addOrUpdateRepos(repos: List<Repo>)
}

class RoomDB : LocalDB {
    override fun getRepos(): List<Repo> {
        TODO("Not yet implemented")
    }

    override fun getPullRequests(repoId: Long): RepoPullRequest {
        TODO("Not yet implemented")
    }

    override fun addOrUpdateRepos(repos: List<Repo>) {
        TODO("Not yet implemented")
    }

}