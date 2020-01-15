package studio.forface.viewstatestore.utils

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Rule

/**
 * Interface for Test with [androidx.arch]
 * It uses [InstantTaskExecutorRule]
 *
 * @author Davide Farella
 */
internal interface ArchTest {

    @get:Rule val archRule get() = InstantTaskExecutorRule()
}
