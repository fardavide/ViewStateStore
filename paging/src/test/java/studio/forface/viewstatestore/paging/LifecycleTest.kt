package studio.forface.viewstatestore.paging

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagedList
import androidx.paging.PositionalDataSource
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

/**
 * @author Davide Giuseppe Farella
 * A class for test `ViewStateStore` observing through `Lifecycle` events.
 */
internal class LifecycleTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Test
    fun `PagedViewStateStore emits correctly through the Lifecycle`() {
        val vss = PagedViewStateStore<Int>()

        // Setup Android test components
        val observer = mockk<(PagedList<Int>) -> Unit>( relaxed = true )
        val lifecycle = TestLifecycle()

        // Publish to vss
        vss.setDataSource( mockk {
            every { create() } answers { mockk<PositionalDataSource<Int>>( relaxed = true ) }
        } )

        // Start observing
        vss.observeData( lifecycle, observer )

        // Should not be called since lifecycle Resume event has not been called
        verify( exactly = 0 ) { observer( any() ) }

        // Resume the Lifecycle
        lifecycle.resume()
        // Should BE called since lifecycle Resume event HAS BEEN called
        verify( exactly = 1 ) { observer( any() ) }

        // Destroy and Create the lifecycle
        lifecycle.destroy()
        lifecycle.create()

        // Re-start observing
        vss.observeData( lifecycle, observer )

        // Resume the Lifecycle
        lifecycle.resume()
        // Should BE called twice lifecycle Resume event HAS BEEN called again
        verify( exactly = 2 ) { observer( any() ) }
    }
}