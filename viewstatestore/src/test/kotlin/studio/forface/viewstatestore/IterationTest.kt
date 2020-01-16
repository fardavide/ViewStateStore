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
 * A class for test [ViewStateStore.iterator].
 * @author Davide Giuseppe Farella
 */
internal class IterationTest : ArchTest, CoroutinesTest {

    @Test
    fun `ViewStateStore iterator`() = runBlocking {
        val results = mutableListOf<ViewState<Int>>()

        val vss = ViewStateStore(0)
        val j = GlobalScope.async {
            for (state in vss) results += state
        }
        delay(10); vss.set(1)
        delay(10); vss.set(2)
        delay(10);

        j.cancel()
        assertEquals(listOf(
            ViewState(0),
            ViewState(1),
            ViewState(2)
        ), results.toList())
    }

    @Test
    fun `ViewStateStore data_iterator`() = runBlocking {
        val results = mutableListOf<Int>()

        val vss = ViewStateStore(0)
        val j = GlobalScope.async {
            for (state in vss.data) results += state
        }
        delay(10); vss.set(1)
        delay(10); vss.set(2)
        delay(10);

        j.cancel()
        assertEquals(listOf(
            0,
            1,
            2
        ), results.toList())
    }

    @Test
    fun `ViewStateStore composed_iterator`() = runBlocking {
        val results = mutableListOf<Int>()

        val vss = ViewStateStore(0)
        val j = GlobalScope.async {
            for ((data, _, _) in vss.composed) results += data!!
        }
        delay(10); vss.set(1)
        delay(10); vss.set(2)
        delay(10);

        j.cancel()
        assertEquals(listOf(
            0,
            1,
            2
        ), results.toList())
    }
}
