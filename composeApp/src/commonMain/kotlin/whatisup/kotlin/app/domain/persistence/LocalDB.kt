package whatisup.kotlin.app.domain.persistence

import whatisup.kotlin.app.domain.persistence.models.Repo
import whatisup.kotlin.app.domain.persistence.models.RepoPullRequest

interface LocalDB {
    fun getRepos(): List<Repo>
    fun getPullRequests(repoId: Long): RepoPullRequest
}