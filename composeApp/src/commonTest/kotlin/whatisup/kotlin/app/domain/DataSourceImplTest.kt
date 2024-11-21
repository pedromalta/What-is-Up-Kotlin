package whatisup.kotlin.app.domain

import com.badoo.reaktive.test.scheduler.TestScheduler
import dev.mokkery.answering.returns
import dev.mokkery.answering.sequentiallyReturns
import dev.mokkery.every
import dev.mokkery.everySuspend
import dev.mokkery.matcher.any
import dev.mokkery.mock
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.serialization.json.Json
import whatisup.kotlin.app.data.api.kotlinReposPage1
import whatisup.kotlin.app.data.api.kotlinReposPage2
import whatisup.kotlin.app.data.api.models.RepoList
import whatisup.kotlin.app.data.api.services.GithubApi
import whatisup.kotlin.app.data.mappers.RepoApiPersistenceMapper
import whatisup.kotlin.app.data.persistence.LocalDB
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class DataSourceImplTest {

    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    private val testingScheduler = TestScheduler(isManualProcessing = true)

    private val mockApiResultPage1 = json.decodeFromString<RepoList>(kotlinReposPage1)
    private val mockApiResultPage2 = json.decodeFromString<RepoList>(kotlinReposPage2)

    private val mockApiDBResultPage1 = mockApiResultPage1
        .items.map { RepoApiPersistenceMapper(1).transform(it) }

    private val mockApiDBResultPage2 = mockApiResultPage2
        .items.map { RepoApiPersistenceMapper(1).transform(it) }

    // TODO Mock muito atrelado a implementação do DataSourceImpl, teste fica quebradiço, refatorar com um implementação fake da DB
    private val db = mock<LocalDB> {
        every { getRepos(1) } sequentiallyReturns listOf(
            emptyList(),
            emptyList(),
            mockApiDBResultPage1,
            mockApiDBResultPage1,
            mockApiDBResultPage1

        )
        every { getRepos(2) } sequentiallyReturns listOf(
            emptyList(),
            mockApiDBResultPage2,
            mockApiDBResultPage2,
            mockApiDBResultPage2
        )
        every { addOrUpdateRepos(any()) } returns Unit
    }

    private val api = mock<GithubApi> {
        everySuspend { searchRepositories(page = 1) } returns mockApiResultPage1
        everySuspend { searchRepositories(page = 2) } returns mockApiResultPage2
    }

    private val dataSource = DataSourceImpl(db, api, testingScheduler)

    @OptIn(ExperimentalCoroutinesApi::class, DelicateCoroutinesApi::class)
    @BeforeTest
    fun before() {
        Dispatchers.setMain(newSingleThreadContext("Test thread"))
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @AfterTest
    fun after() {
        Dispatchers.resetMain()
    }


    @Test
    fun `fetchRepoList emits repo data and updates repoListSubject`() = runTest {
        launch(Dispatchers.Main) {

            val repoList = dataSource.repoListSubject.value
            assertTrue(repoList.isEmpty(), "Repo list should be empty initially")

            dataSource.fetchRepoList(page = 1)
            testingScheduler.process()

            val firstPageResult = dataSource.repoListSubject.value
            assertEquals(30, firstPageResult.size, "Repo list should now contain 30 items, 1 page")

            dataSource.fetchRepoList(page = 2)
            testingScheduler.process()

            val secondPageResult = dataSource.repoListSubject.value
            assertEquals(60, secondPageResult.size, "Repo list should contain 60 items, 2 pages")

            dataSource.fetchRepoList(page = 1)
            testingScheduler.process()

            val cachePageResult = dataSource.repoListSubject.value
            assertEquals(60, cachePageResult.size, "Repo list should still contain 60 items, 2 pages")

        }
    }


}