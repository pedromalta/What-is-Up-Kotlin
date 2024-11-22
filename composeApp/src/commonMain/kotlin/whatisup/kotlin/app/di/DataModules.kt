package whatisup.kotlin.app.di


import com.badoo.reaktive.scheduler.computationScheduler
import io.ktor.client.HttpClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.dsl.module
import whatisup.kotlin.app.data.api.services.DefaultHttpClient
import whatisup.kotlin.app.data.api.services.GithubApi
import whatisup.kotlin.app.data.api.services.GithubApiImpl
import whatisup.kotlin.app.data.mocks.MockDB
import whatisup.kotlin.app.data.persistence.LocalDB
import whatisup.kotlin.app.domain.datasource.RepoListDataSource
import whatisup.kotlin.app.domain.datasource.RepoListDataSourceImpl
import whatisup.kotlin.app.domain.datasource.RepoPullRequestsDataSource
import whatisup.kotlin.app.domain.datasource.RepoPullRequestsDataSourceImpl

class DataModules {

    private val remoteDatasource = module {
        single<HttpClient> { DefaultHttpClient().get() }
        single<GithubApi> { GithubApiImpl(Dispatchers.IO, get()) }
    }

    private val localDatasource = module {
        single<LocalDB> { MockDB() }
    }

    private val repoListDataSource = module {
        factory<RepoListDataSource> { RepoListDataSourceImpl(get(), get(), computationScheduler) }
    }

    private val repoPullRequestsDataSource = module {
        factory<RepoPullRequestsDataSource> { RepoPullRequestsDataSourceImpl(get(), get(), computationScheduler) }
    }

    val dataSource = remoteDatasource + localDatasource + repoListDataSource + repoPullRequestsDataSource

}
