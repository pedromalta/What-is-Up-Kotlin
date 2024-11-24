package whatisup.kotlin.app.domain

import com.badoo.reaktive.test.scheduler.TestScheduler
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import whatisup.kotlin.app.data.api.githubApiMock
import whatisup.kotlin.app.data.persistence.MockDB
import whatisup.kotlin.app.domain.datasource.RepositoriesDataSourceImpl
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * Tests for [RepositoriesDataSourceImpl]
 */
class RepositoriesDataSourceImplTest {

    private val testingScheduler = TestScheduler(isManualProcessing = true)

    //TODO use Koin test to inject and control these dependencies
    private val dataSource = RepositoriesDataSourceImpl(MockDB(), githubApiMock, testingScheduler)

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
    fun `fetchRepositories emits Repositories to repositoriesObservable`() = runTest {
        launch(Dispatchers.Main) {

            val repoList = dataSource.repositoriesObservable.value
            assertTrue(repoList.isEmpty(), "Repositories should be empty initially")

            dataSource.fetchRepositories(page = 1)
            testingScheduler.process()

            val firstPageResult = dataSource.repositoriesObservable.value
            assertEquals(30, firstPageResult.size, "Repositories should now contain 30 items, 1 page")

            dataSource.fetchRepositories(page = 2)
            testingScheduler.process()

            val secondPageResult = dataSource.repositoriesObservable.value
            assertEquals(60, secondPageResult.size, "Repositories should contain 60 items, 2 pages")

            dataSource.fetchRepositories(page = 1)
            testingScheduler.process()

            val cachePageResult = dataSource.repositoriesObservable.value
            assertEquals(60, cachePageResult.size, "Repositories should still contain 60 items, 2 pages")

        }
    }


}