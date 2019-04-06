package studio.forface.viewstatestore

import kotlin.test.*

/**
 * Test class for [ErrorStateGenerator]
 *
 * @author Davide Giuseppe Farella
 */
internal class ErrorResolutionTest {

    @BeforeTest
    fun resetConfig() {
        ViewStateStoreConfig.errorStateGenerator = { default }
    }

    /** @return [ViewState.Error] generated for the given [throwable] and [ErrorResolution] */
    private fun createError( throwable: Throwable, resolution: ErrorResolution? = null ) =
        ViewState.Error.fromThrowable( throwable, resolution )

    @Test
    fun simpleErrorResolution() {
        class NPError( throwable: Throwable ) : ViewState.Error( throwable )
        class SyncError( throwable: Throwable ) : ViewState.Error( throwable )

        val mockResolution = {}

        ViewStateStoreConfig.errorStateGenerator = {
            when( it ) {
                is NullPointerException ->NPError( it )
                is IllegalArgumentException ->SyncError( it )
                else -> default
            }
        }

        assertFalse( createError( NullPointerException() ).hasResolution() )
        assertTrue( createError( IllegalArgumentException(), mockResolution ).hasResolution() )
        assertSame( mockResolution, createError( IllegalArgumentException(), mockResolution ).getResolution() )
    }
}