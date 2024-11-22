package whatisup.kotlin.app.ui.model

data class Repo(
    override val id: Long,
    val name: String,
    val ownerLogin: String,
    val description: String,
    val forksCount: Int,
    val starsCount: Int,
): StableId {

}