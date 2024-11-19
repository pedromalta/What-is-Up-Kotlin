package whatisup.kotlin.app.data

import com.badoo.reaktive.subject.Subject
import com.badoo.reaktive.subject.publish.PublishSubject
import whatisup.kotlin.app.data.api.services.GithubApi
import whatisup.kotlin.app.data.models.Repo
import whatisup.kotlin.app.data.models.RepoPullRequest
import whatisup.kotlin.app.data.persistence.LocalDB

interface DataSource {
    val repoListSubject: Subject<List<Repo>>
    val repoPullRequestSubject: Subject<RepoPullRequest>

    fun fetchRepoList(page: Int)
    fun fetchRepoPullRequest(owner: String, repo: String)
}

class DataSourceImpl(
    private val db: LocalDB,
    private val api: GithubApi,
) : DataSource {

    override val repoListSubject: Subject<List<Repo>> = PublishSubject()
    override val repoPullRequestSubject: Subject<RepoPullRequest> = PublishSubject()

    override fun fetchRepoList(page: Int) {
        TODO("Not yet implemented")
    }

    override fun fetchRepoPullRequest(owner: String, repo: String) {
        TODO("Not yet implemented")
    }

}