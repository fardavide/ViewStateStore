package studio.forface.viewstatestore.paging

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.DataSource
import androidx.paging.PagedList
import androidx.paging.PositionalDataSource
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import studio.forface.viewstatestore.ViewState
import studio.forface.viewstatestore.ViewStateStore
import kotlin.test.assertEquals

/**
 * A class for test that `PagedViewStateStore` emits correctly data from both `DataSource` and manual publishing
 * @author Davide Giuseppe Farella
 */
internal class PublishingTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Test
    fun `PagedViewStateStore emits correctly from manual publishing`() {
        val dsf = mockk<DataSource.Factory<Int, Int>> {
            every { create() } answers { mockk<PositionalDataSource<Int>>(relaxed = true) }
        }
        val vss = ViewStateStore.from(dsf)
        val observer = mockk<(ViewState<PagedList<Int>>) -> Unit>(relaxed = true)

        // Start observing
        vss.observeForever { doOnEach(observer) }

        // Publish mock data to ViewStateStore
        val mockData = mockk<PagedList<Int>>()
        vss.setData(mockData)
        // Verify observer has been called once
        verify(exactly = 1) { observer(any()) }
        // Assert ViewStateStore has right value
        assertEquals(mockData, vss.unsafeState().data)

        // Publish loading to ViewStateStore
        vss.setLoading()
        // Verify observer has been called once
        verify(exactly = 2) { observer(any()) }
        // Assert ViewStateStore has right value
        assertEquals(ViewState.Loading, vss.unsafeState())

        // Publish loading to ViewStateStore
        vss.setError(Exception())
        // Verify observer has been called twice
        verify(exactly = 3) { observer(any()) }
        // Assert ViewStateStore has right value
        assert(vss.unsafeState() is ViewState.Error)
    }
}
