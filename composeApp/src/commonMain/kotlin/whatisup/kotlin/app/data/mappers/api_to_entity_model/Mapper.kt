package whatisup.kotlin.app.data.mappers.api_to_entity_model

interface Mapper<in T, out E> {
    fun transform(origin: T): E
}

fun Long.toBoolean() = this == 1L