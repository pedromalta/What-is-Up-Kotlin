package whatisup.kotlin.app.di


import com.badoo.reaktive.scheduler.computationScheduler
import io.ktor.client.HttpClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.core.module.Module
import org.koin.dsl.module
import whatisup.kotlin.app.data.api.services.DefaultHttpClient
import whatisup.kotlin.app.data.api.services.GithubApi
import whatisup.kotlin.app.data.api.services.GithubApiImpl
import whatisup.kotlin.app.data.db.AppDatabase
import whatisup.kotlin.app.data.persistence.LocalDB
import whatisup.kotlin.app.data.persistence.SQLiteDB
import whatisup.kotlin.app.data.persistence.createDatabase
import whatisup.kotlin.app.domain.datasource.PullRequestsDataSource
import whatisup.kotlin.app.domain.datasource.PullRequestsDataSourceImpl
import whatisup.kotlin.app.domain.datasource.RepositoriesDataSource
import whatisup.kotlin.app.domain.datasource.RepositoriesDataSourceImpl

expect val sqlDriverModule: Module

/**
 * Data Modules Dependency Injection
 */
class DataModules {

    private val remoteDatasource = module {
        single<HttpClient> { DefaultHttpClient().get() }
        single<GithubApi> { GithubApiImpl(Dispatchers.IO, get()) }
    }

    private val localDatasource = sqlDriverModule + module {
        single<AppDatabase> { createDatabase(get()) }
        single<LocalDB> { SQLiteDB(get()) }
    }

    private val repositoriesDataSource = module {
        factory<RepositoriesDataSource> { RepositoriesDataSourceImpl(get(), get(), computationScheduler) }
    }

    private val pullRequestsDataSource = module {
        factory<PullRequestsDataSource> { PullRequestsDataSourceImpl(get(), get(), computationScheduler) }
    }

    /**
     * modules responsible for data request and cache
     */
    val modules = remoteDatasource + localDatasource + repositoriesDataSource + pullRequestsDataSource

}
