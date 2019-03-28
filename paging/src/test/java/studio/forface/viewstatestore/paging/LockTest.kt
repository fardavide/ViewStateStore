package studio.forface.viewstatestore.paging

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagedList
import io.mockk.mockk
import org.junit.Rule
import studio.forface.viewstatestore.*
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * Test class for [LockedViewStateStore]
 *
 * @author Davide Giuseppe Farella.
 */
internal class LockTest {

    @get: Rule val rule = InstantTaskExecutorRule()

    @Test
    fun `test lock methods from LockedViewStateStore can NOT be called without a ViewStateStoreScope`() {
        val store = PagedViewStateStore<String>()
        val lockedStore = store.lock
        store.setLoading()

        assertEquals( ViewState.Loading, lockedStore.state() )
        // lockedStore.setState and lockedStore.postState cannot be called
    }

    @Test
    fun `test lock methods from LockedViewStateStore can be called within a ViewStateStoreScope`() {
        class TestScope : ViewStateStoreScope {
            fun setLoadingTo( store: LockedViewStateStore<PagedList<String>> ) {
                store.setLoading()
            }
        }

        val lockedStore = PagedViewStateStore<String>().lock
        TestScope().setLoadingTo( lockedStore )

        assertEquals( ViewState.Loading, lockedStore.state() )
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