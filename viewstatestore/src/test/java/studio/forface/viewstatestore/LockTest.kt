package studio.forface.viewstatestore

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Rule
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
        val store = ViewStateStore("banana" )
        val lockedStore = store.lock
        store.setData("banana" )

        assertEquals("banana", lockedStore.state()?.data )
        // lockedStore.setState and lockedStore.postState cannot be called
    }

    @Test
    fun `test lock methods from LockedViewStateStore can be called within a ViewStateStoreScope`() {
        class TestScope : ViewStateStoreScope {
            fun setBananaTo( store: LockedViewStateStore<String> ) {
                store.setData("banana" )
            }
        }

        val lockedStore = ViewStateStore("banana" ).lock
        TestScope().setBananaTo( lockedStore )

        assertEquals("banana", lockedStore.state()?.data )
    }
}