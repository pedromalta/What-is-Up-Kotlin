package whatisup.kotlin.app.ui.model

data class Repository(
    override val id: Long,
    val name: String,
    val ownerLogin: String,
    val description: String,
    val forksCount: Long,
    val starsCount: Long,
): StableId {

}