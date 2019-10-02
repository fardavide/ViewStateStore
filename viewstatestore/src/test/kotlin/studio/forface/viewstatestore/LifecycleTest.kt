package studio.forface.viewstatestore

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.mockk
import io.mockk.verify
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import studio.forface.viewstatestore.utils.TestLifecycle

/**
 * A class for test `ViewStateStore` observing through `Lifecycle` events.
 * @author Davide Giuseppe Farella
 */
internal class LifecycleTest {
    @get:Rule var rule: TestRule = InstantTaskExecutorRule()

    @Test
    fun `does not emit without active observers`() = with(setup) {
        // Publish 1 to vss
        vss.set(1)
        // Should not be called since lifecycle Resume event has not been called
        verify(exactly = 0) { observer(1) }
    }

    @Test
    fun `emits correctly after observer became active`() = with(setup) {
        // Publish 1 to vss
        vss.set(1)

        // Resume the Lifecycle
        lifecycle.resume()
        // Should BE called since lifecycle Resume event HAS BEEN called
        verify(exactly = 1) { observer(1) }
    }

    @Test
    fun `emits again after observer became active after destroy`() = with(setup) {
        // Publish 1 to vss
        vss.set(1)

        // Resume the Lifecycle
        lifecycle.resume()
        // Should BE called since lifecycle Resume event HAS BEEN called
        verify(exactly = 1) { observer(1) }

        // Destroy and Create the lifecycle
        lifecycle.destroy()
        lifecycle.create()

        // Re-start observing
        vss.observeData(lifecycle, observer)

        // Resume the Lifecycle
        lifecycle.resume()
        // Should BE called twice because lifecycle Resume event HAS BEEN called again
        verify(exactly = 2) { observer(1) }
    }

    @Test
    fun `once emits correctly after observer became active`() = with(setup) {
        // Publish 1 to vss
        vss.setOnce(1)

        // Resume the Lifecycle
        lifecycle.resume()
        // Should BE called since lifecycle Resume event HAS BEEN called
        verify(exactly = 1) { observer(1) }
    }

    @Test
    fun `once does not emit after observer became active after destroy`() = with(setup) {
        // Publish 1 to vss
        vss.setOnce(1)

        // Resume the Lifecycle
        lifecycle.resume()
        // Should BE called since lifecycle Resume event HAS BEEN called
        verify(exactly = 1) { observer(1) }

        // Destroy and Create the lifecycle
        lifecycle.destroy()
        lifecycle.create()

        // Re-start observing
        vss.observeData(lifecycle, observer)

        // Resume the Lifecycle
        lifecycle.resume()
        // Should NOT BE called because once option has been used
        verify(exactly = 1) { observer(1) }
    }

    @Test
    fun `once emits correctly multiple values`() = with(setup) {
        // Resume the Lifecycle
        lifecycle.resume()

        // Publish 1 to vss
        vss.setOnce(1)
        // Should BE called since lifecycle Resume event HAS BEEN called
        verify(exactly = 1) { observer(1) }

        // Publish 1 to vss
        vss.setOnce(1)
        // Should BE called again since a new value has been published
        verify(exactly = 2) { observer(1) }
    }
}

private class Setup {
    // Setup Android test components
    val observer = mockk<(Int) -> Unit>(relaxed = true)
    val lifecycle = TestLifecycle()

    // Start Observing by default
    val vss = ViewStateStore<Int>().apply { observeData(lifecycle, observer) }
}
private val setup get() = Setup()
