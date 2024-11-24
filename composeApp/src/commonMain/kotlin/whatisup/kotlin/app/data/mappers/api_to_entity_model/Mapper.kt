package whatisup.kotlin.app.data.mappers.api_to_entity_model

/**
 * Basic Mapping interface
 */
interface Mapper<in T, out E> {
    fun transform(origin: T): E
}

/**
 * Convert Long values of 1L to TRUE otherwise FALSE
 */
fun Long.toBoolean() = this == 1L