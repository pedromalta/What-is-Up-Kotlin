package whatisup.kotlin.app.data.api.services

import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import io.ktor.util.logging.KtorSimpleLogger
import kotlinx.serialization.json.Json

/**
 * Returns the [HttpClientEngine] for the platform
 */
expect fun getPlatformHttpClientEngine(): HttpClientEngine

private const val TAG = "HTTP Client"
private const val URL = "api.github.com"
private const val PORT = 443

/**
 * Default Ktor [HttpClient] for the project, it's tuned to reach [URL] and [PORT]
 */
class DefaultHttpClient(
    engine: HttpClientEngine = getPlatformHttpClientEngine()
) {
    private val githubApiClient: HttpClient = HttpClient(engine) {
        install(HttpTimeout) {
            socketTimeoutMillis = 60_000
            requestTimeoutMillis = 60_000
        }

        install(Logging) {
            level = LogLevel.INFO
            logger = object : Logger {
                override fun log(message: String) {
                    Napier.d(message =
"""--------- Start Request -----------
$message
--------- End Request -------------""",
                        tag = TAG
                    )
                }
            }
        }

        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }

        defaultRequest {
            contentType(ContentType.Application.Json)
            url {
                protocol = URLProtocol.HTTPS
                host = URL
                port = PORT
            }
        }
    }

    fun get() = githubApiClient
}
