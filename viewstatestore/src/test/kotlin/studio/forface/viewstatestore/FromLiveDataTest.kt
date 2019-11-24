package studio.forface.viewstatestore

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
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
    fun `ViewStateStore emits correctly from MutableLiveData`() {
        val liveData = MutableLiveData<Int>()
        val vss = ViewStateStore.from(liveData)

        // Start Observing
        val observer = mockk<(Int) -> Unit>(relaxed = true)
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

    @Test
    fun `LiveData_switchMap works correctly`() {
        val s1 = MutableLiveData<Int>()
        val s2 = MutableLiveData<Int>()
        val m = s1.switchMap { v1 -> s2.map { v2 -> v1 * v2 } }

        // Start Observing
        val observer = mockk<(Int) -> Unit>(relaxed = true)
        m.observeForever(observer)

        verify(exactly = 0) { observer(any()) }

        // Verity very first emit
        s1.value = 1
        verify(exactly = 0) { observer(any()) }
        s2.value = 1
        verify(exactly = 1) { observer(any()) }
        verify(exactly = 1) { observer(1) }

        // Verify emit on s1
        s1.value = 2
        verify(exactly = 2) { observer(any()) }
        verify(exactly = 1) { observer(2) }

        // Verify emit on s2
        s2.value = 2
        verify(exactly = 3) { observer(any()) }
        verify(exactly = 1) { observer(4) }

        // Re-verify emit on s1
        s1.value = 3
        verify(exactly = 4) { observer(any()) }
        verify(exactly = 1) { observer(6) }
    }

    @Test
    fun `ViewStateStore emits correctly from LiveData_switchMap`() {
        val s1 = MutableLiveData<Int>()
        val s2 = MutableLiveData<Int>()
        val m = ViewStateStore.from(s1.switchMap { v1 -> s2.map { v2 -> v1 * v2 } })

        // Start Observing
        val observer = mockk<(Int) -> Unit>(relaxed = true)
        m.observeDataForever(observer)

        verify(exactly = 0) { observer(any()) }

        // Verity very first emit
        s1.value = 1
        verify(exactly = 0) { observer(any()) }
        s2.value = 1
        verify(exactly = 1) { observer(any()) }
        verify(exactly = 1) { observer(1) }

        // Verify emit on s1
        s1.value = 2
        verify(exactly = 2) { observer(any()) }
        verify(exactly = 1) { observer(2) }

        // Verify emit on s2
        s2.value = 2
        verify(exactly = 3) { observer(any()) }
        verify(exactly = 1) { observer(4) }

        // Re-verify emit on s1
        s1.value = 3
        verify(exactly = 4) { observer(any()) }
        verify(exactly = 1) { observer(6) }
    }
}
