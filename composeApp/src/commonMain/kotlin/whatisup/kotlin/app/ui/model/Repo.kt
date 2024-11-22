package whatisup.kotlin.app.ui.model

import whatisup.kotlin.app.ui.components.StableId

data class Repo(
    override val id: Long,
    val name: String,
    val fullName: String,
): StableId