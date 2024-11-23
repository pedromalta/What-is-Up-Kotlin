package whatisup.kotlin.app.data.persistence

import app.cash.sqldelight.db.SqlDriver
import whatisup.kotlin.app.data.api.models.PullRequestApiModel
import whatisup.kotlin.app.data.api.models.RepositoryApiModel
import whatisup.kotlin.app.data.db.AppDatabase
import whatisup.kotlin.app.data.mappers.api_to_entity_model.PullRequestMapper
import whatisup.kotlin.app.data.mappers.api_to_entity_model.RepositoryMapper
import whatisup.kotlin.app.data.mappers.api_to_entity_model.UserMapper
import whatisup.kotlin.app.data.mappers.entity_to_domain_model.GetByRepoIdQueryRowToPullRequest
import whatisup.kotlin.app.data.mappers.entity_to_domain_model.GetPageQueryRowToRepository
import whatisup.kotlin.app.domain.datasource.RepositoriesDataSource
import whatisup.kotlin.app.domain.models.PullRequestModel
import whatisup.kotlin.app.domain.models.RepositoryModel


fun createDatabase(driver: SqlDriver): AppDatabase {
    return AppDatabase(driver)
}

class SQLightDB(appDatabase: AppDatabase): LocalDB {

    private val repositoryQueries = appDatabase.repositoryQueries
    private val pullRequestQueries = appDatabase.pullRequestQueries
    private val userQueries = appDatabase.userQueries

    override fun getRepositories(page: Int): List<RepositoryModel> {
        val offset = RepositoriesDataSource.PER_PAGE * (page - 1L)

        val query = repositoryQueries.getPage(offset).executeAsList()
        val mapper = GetPageQueryRowToRepository()
        return query.map { row -> mapper.transform(row) }
    }

    override fun getPullRequests(repoId: Long): List<PullRequestModel> {
        val query = pullRequestQueries.getByRepoId(repoId).executeAsList()
        val mapper = GetByRepoIdQueryRowToPullRequest()
        return query.map { row -> mapper.transform(row) }
    }

    override fun addOrUpdateRepositories(repositories: List<RepositoryApiModel>) {
        val repositoryMapper = RepositoryMapper()
        val userMapper = UserMapper()
        repositoryQueries.transaction {
            repositories.forEach { apiRepository ->
                val repository = repositoryMapper.transform(apiRepository)
                val user = userMapper.transform(apiRepository.owner)
                userQueries.insertOrReplace(user)
                repositoryQueries.insertOrReplace(repository)
            }
        }
    }

    override fun addOrUpdatePullRequests(
        pullRequests: List<PullRequestApiModel>
    ) {
        val pullRequestMapper = PullRequestMapper()
        val userMapper = UserMapper()
        pullRequestQueries.transaction {
            pullRequests.forEach { apiPullRequest ->
                val pullRequest = pullRequestMapper.transform(apiPullRequest)
                val user = userMapper.transform(apiPullRequest.user)
                userQueries.insertOrReplace(user)
                pullRequestQueries.insertOrReplace(pullRequest)
            }
        }
    }
}
