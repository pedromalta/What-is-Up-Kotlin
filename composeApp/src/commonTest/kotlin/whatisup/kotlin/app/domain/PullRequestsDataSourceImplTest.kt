package whatisup.kotlin.app.domain

import com.badoo.reaktive.test.observable.test
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
import whatisup.kotlin.app.data.mocks.MockDB
import whatisup.kotlin.app.domain.datasource.PullRequestsDataSourceImpl
import whatisup.kotlin.app.domain.models.PullRequestsModel
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class PullRequestsDataSourceImplTest {

    private val testingScheduler = TestScheduler(isManualProcessing = true)

    //TODO use Koin test to inject and control these dependencies
    private val dataSource = PullRequestsDataSourceImpl(MockDB(), githubApiMock, testingScheduler)

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
    fun `fetchRepoList emits repo data and updates pullRequestsSubject`() = runTest {
        launch(Dispatchers.Main) {

            val repoPullRequestsSubject = dataSource.pullRequestsSubject.test()
            assertEquals(repoPullRequestsSubject.values.first(), PullRequestsModel.EMPTY, "Pull Requests should be null initially")

            dataSource.fetchPullRequests(
                repoId = 3432266,
                owner = "JetBrains",
                repo = "kotlin"
            )
            testingScheduler.process()

            assertNotEquals(repoPullRequestsSubject.values.last(), PullRequestsModel.EMPTY, "Pull Requests should be fetched")

        }
    }


}