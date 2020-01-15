package studio.forface.viewstatestore.utils

import org.junit.Rule

/**
 * Interface for Test with [kotlin.coroutines]
 * It uses [CoroutinesTestRule]
 *
 * @author Davide Farella
 */
internal interface CoroutinesTest {

    @get:Rule val coroutinesRule get() = CoroutinesTestRule()
}
