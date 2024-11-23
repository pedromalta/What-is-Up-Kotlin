package whatisup.kotlin.app.di

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import org.koin.core.module.Module
import org.koin.dsl.module
import whatisup.kotlin.app.data.db.AppDatabase

actual val sqlDriverModule: Module = module {
    single<SqlDriver> {
        NativeSqliteDriver(
            schema = AppDatabase.Schema,
            name = "app_database.db"
        )
    }
}