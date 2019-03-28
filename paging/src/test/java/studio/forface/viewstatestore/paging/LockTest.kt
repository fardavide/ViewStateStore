package studio.forface.viewstatestore.paging

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.DataSource
import androidx.paging.PagedList
import io.mockk.mockk
import org.junit.Rule
import studio.forface.viewstatestore.LockedViewStateStore
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

/**
 * Test class for [LockedViewStateStore]
 *
 * @author Davide Giuseppe Farella.
 */
internal class LockTest {

    @get: Rule val rule = InstantTaskExecutorRule()

    private val mockDataSource = mockk<DataSource.Factory<Int, String>>( relaxed = true )

    @Test
    fun `test lock methods from LockedPagedViewStateStore can NOT be called without a PagedViewStateStoreScope`() {
        val store = PagedViewStateStore<String>()
        val lockedStore = store.lock
        store.setDataSource( mockDataSource )

        lockedStore.pagedLiveData
        // lockedStore.setDataSource cannot be called
    }

    @Test
    fun `test lock methods from LockedPagedViewStateStore can be called within a PagedViewStateStoreScope`() {
        class TestScope : PagedViewStateStoreScope {
            fun setDataSourceTo( store: LockedPagedViewStateStore<String> ) {
                store.setDataSource( mockDataSource )
                store.setLoading()
            }
        }

        val lockedStore = PagedViewStateStore<String>().lock
        TestScope().setDataSourceTo( lockedStore )

        assertNotNull( lockedStore.state() )
    }

    @Test
    fun `test type on PagedViewStateStore-lock`() {
        val mockPagedList = mockk<PagedList<Int>>()

        val store = PagedViewStateStore<Int>()
        val lockedStore = store.lock

        store.setData( mockPagedList )

        assertEquals( mockPagedList, store.unsafeState().data )
        assertEquals( mockPagedList, lockedStore.unsafeState().data )
    }
}