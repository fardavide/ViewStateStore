package studio.forface.viewstatestore

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.mockk
import io.mockk.verify
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import studio.forface.viewstatestore.utils.TestLifecycle

/**
 * @author Davide Giuseppe Farella
 * A class for test `ViewStateStore` observing through `Lifecycle` events.
 */
internal class LifecycleTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Test
    fun `ViewStateStore emits correctly through the Lifecycle`() {
        val vss = ViewStateStore<Int>()

        // Setup Android test components
        val observer = mockk<(Int) -> Unit>( relaxed = true )
        val lifecycle = TestLifecycle()

        // Start observing
        vss.observeData( lifecycle, observer )
        // Should not be called since no data has been published to vss
        verify( exactly = 0 ) { observer( 1 ) }

        // Publish 1 to vss
        vss.setData( 1 )
        // Should not be called since lifecycle Resume event has not been called
        verify( exactly = 0 ) { observer( 1 ) }

        // Resume the Lifecycle
        lifecycle.resume()
        // Should BE called since lifecycle Resume event HAS BEEN called
        verify( exactly = 1 ) { observer( 1 ) }

        // Destroy and Create the lifecycle
        lifecycle.destroy()
        lifecycle.create()

        // Re-start observing
        vss.observeData( lifecycle, observer )

        // Resume the Lifecycle
        lifecycle.resume()// Should BE called twice lifecycle Resume event HAS BEEN called again
        verify( exactly = 2 ) { observer( 1 ) }
    }
}