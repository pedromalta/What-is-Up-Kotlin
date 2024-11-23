package whatisup.kotlin.app.di

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import org.koin.core.module.Module
import org.koin.dsl.module
import whatisup.kotlin.app.data.db.AppDatabase

actual val sqlDriverModule: Module = module {
    single<SqlDriver> {
        AndroidSqliteDriver(
            schema = AppDatabase.Schema,
            context = get(),
            name = "app_database"
        )
    }
}