package whatisup.kotlin.app

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform