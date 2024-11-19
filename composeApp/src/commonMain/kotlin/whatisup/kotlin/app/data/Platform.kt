package whatisup.kotlin.app.data

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform