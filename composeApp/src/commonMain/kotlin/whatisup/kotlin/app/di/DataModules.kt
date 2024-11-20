package whatisup.kotlin.app.di

import com.badoo.reaktive.coroutinesinterop.asScheduler
import com.badoo.reaktive.scheduler.ioScheduler
import io.ktor.client.HttpClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.dsl.module
import whatisup.kotlin.app.domain.DataSource
import whatisup.kotlin.app.domain.DataSourceImpl
import whatisup.kotlin.app.data.api.services.DefaultHttpClient
import whatisup.kotlin.app.data.api.services.GithubApi
import whatisup.kotlin.app.data.api.services.GithubApiImpl
import whatisup.kotlin.app.data.persistence.LocalDB
import whatisup.kotlin.app.data.persistence.RoomDB

class DataModules {

    private val remoteDatasource = module {
        single<HttpClient> { DefaultHttpClient().get() }
        single<GithubApi> { GithubApiImpl(Dispatchers.IO, get()) }
    }

    private val localDatasource = module {
        single<LocalDB> { RoomDB() }
    }

    val dataSource = module {
        factory<DataSource> { DataSourceImpl(get(), get(), Dispatchers.IO.asScheduler()) }
    } + remoteDatasource + localDatasource

}
