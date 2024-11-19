package whatisup.kotlin.app.domain

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform