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
import whatisup.kotlin.app.data.db.localDBMock
import whatisup.kotlin.app.domain.datasource.DataSourceImpl
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class DataSourceImplTest {

    private val testingScheduler = TestScheduler(isManualProcessing = true)

    //TODO use Koin test to inject and control these dependencies
    private val dataSource = DataSourceImpl(localDBMock, githubApiMock, testingScheduler)

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