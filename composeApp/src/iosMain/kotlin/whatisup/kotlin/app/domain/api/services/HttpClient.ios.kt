package whatisup.kotlin.app.domain.api.services

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin

actual fun getPlatformHttpClientEngine(): HttpClientEngine = Darwin.create {

}
