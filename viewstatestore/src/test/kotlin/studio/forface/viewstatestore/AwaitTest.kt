@file:Suppress("EXPERIMENTAL_API_USAGE")

package studio.forface.viewstatestore

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Test
import studio.forface.viewstatestore.utils.ArchTest
import studio.forface.viewstatestore.utils.CoroutinesTest
import kotlin.test.assertEquals

/**
 * A class for test [ViewStateStore.await].
 * @author Davide Giuseppe Farella
 */
internal class AwaitTest : ArchTest, CoroutinesTest {

    @Test
    fun `ViewStateStore await`() = runBlocking {
        val vss = ViewStateStore(0)
        val r = GlobalScope.async { vss.await() }
        delay(100)
        vss.set(1)

        assertEquals(ViewState(0), r.await())
    }

    @Test
    fun `ViewStateStore awaitNext`() = runBlocking {
        val vss = ViewStateStore(0)
        val r = GlobalScope.async { vss.awaitNext() }
        delay(100)
        vss.set(1)

        assertEquals(ViewState(1), r.await())
    }

    @Test
    fun `ViewStateStore awaitData`() = runBlocking {
        val vss = ViewStateStore(0)
        val r = GlobalScope.async { vss.awaitData() }
        delay(100)
        vss.setError(Exception())
        vss.set(1)

        assertEquals(0, r.await())
    }

    @Test
    fun `ViewStateStore awaitNextData`() = runBlocking {
        val vss = ViewStateStore(0)
        val r = GlobalScope.async { vss.awaitNextData() }
        delay(100)
        vss.setError(Exception())
        vss.set(1)

        assertEquals(1, r.await())
    }
}
