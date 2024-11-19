package whatisup.kotlin.app.data.mappers

interface Mapper<in T, out E> {
    fun transform(response: T): E
}