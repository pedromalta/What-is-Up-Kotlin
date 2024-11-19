package whatisup.kotlin.app.domain.api.services

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp

actual fun getPlatformHttpClientEngine(): HttpClientEngine = OkHttp.create {

}
