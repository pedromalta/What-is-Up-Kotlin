package whatisup.kotlin.app.data

import com.badoo.reaktive.subject.Subject
import whatisup.kotlin.app.data.models.Repo
import whatisup.kotlin.app.data.models.RepoPullRequest

interface DataSource {
    val repoListSubject: Subject<List<Repo>>
    val repoPullRequestSubject: Subject<RepoPullRequest>

    fun fetchRepoList(page: Int)
    fun fetchRepoPullRequest(owner: String, repo: String)
}