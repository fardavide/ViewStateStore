package studio.forface.viewstatestore

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import io.mockk.mockk
import io.mockk.verify
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import kotlin.test.assertEquals

/**
 * A class for test `ViewStateStore` created from a `LiveData`.
 * @author Davide Giuseppe Farella
 */
internal class FromLiveDataTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Test
    fun `ViewStateStore is created correctly`() {
        val liveData = MutableLiveData<Boolean>()
        ViewStateStore.from(liveData)
    }

    @Test
    fun `ViewStateStore emits correctly`() {
        val liveData = MutableLiveData<Int>()
        val vss = ViewStateStore.from(liveData)

        // Setup observer
        val observer = mockk<(Int) -> Unit>(relaxed = true)
        // Start Observing
        vss.observeDataForever(observer)

        // Publish to ViewStateStore
        vss.setData(0)
        // Verify observer has been called once
        verify(exactly = 1) { observer(any()) }
        // Assert ViewStateStore has right value
        assertEquals(0, vss.unsafeState().data)

        // Publish to LiveData
        liveData.value = 1
        // Verify observer has been called twice
        verify(exactly = 2) { observer(any()) }
        // Assert ViewStateStore has right value
        assertEquals(1, vss.unsafeState().data)
    }
}
