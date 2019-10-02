package studio.forface.viewstatestore

import kotlin.test.BeforeTest
import kotlin.test.Test

/**
 * Test class for [ErrorStateGenerator]
 *
 * @author Davide Giuseppe Farella
 */
internal class ErrorStateGeneratorTest {

    @BeforeTest
    fun resetConfig() {
        ViewStateStoreConfig.errorStateGenerator = { default }
    }

    /** @return [ViewState.Error] generated for the given [throwable] */
    private fun createError( throwable: Throwable ) = ViewState.Error.fromThrowable( throwable )

    @Test
    fun simpleErrorStateGenerator() {
        class NPError( throwable: Throwable ) : ViewState.Error( throwable )
        class IAError( throwable: Throwable ) : ViewState.Error( throwable )

        ViewStateStoreConfig.errorStateGenerator = {
            when( it ) {
                is NullPointerException ->NPError( it )
                is IllegalArgumentException -> IAError( it )
                else -> default
            }
        }

        assert( createError( NullPointerException() ) is NPError )
        assert( createError( IllegalArgumentException() ) is IAError )

        assert( createError( ClassCastException() ) !is NPError )
        assert( createError( ClassCastException() ) !is IAError )
        assert( createError( ClassCastException() ) is ViewState.DefaultError )
    }

    @Test
    fun multiErrorStateGenerator() {
        class NPError( throwable: Throwable ) : ViewState.Error( throwable )
        class IAError( throwable: Throwable ) : ViewState.Error( throwable )
        class CCError( throwable: Throwable ): ViewState.Error( throwable )

        val generator1: ErrorStateGenerator = {
            when( it ) {
                is NullPointerException ->NPError( it )
                else -> default
            }
        }

        val generator2: ErrorStateGenerator = {
            when( it ) {
                is IllegalArgumentException ->IAError( it )
                else -> default
            }
        }

        val generator3: ErrorStateGenerator = {
            when( it ) {
                is ClassCastException ->CCError( it )
                else -> default
            }
        }

        ViewStateStoreConfig.errorStateGenerator = generator1 + generator2 + generator3

        assert( createError( NullPointerException() ) is NPError )
        assert( createError( IllegalArgumentException() ) is IAError )
        assert( createError( ClassCastException() ) is CCError )
        assert( createError( Exception() ) is ViewState.DefaultError )
    }
}