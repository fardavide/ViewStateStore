@file:Suppress("unused")

package studio.forface.viewstatestore.paging

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Lifecycle.Event.*
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry

/**
 * @author Davide Giuseppe Farella
 * A class for test within a [LifecycleOwner]
 *
 * Inherit from [Lifecycle]
 */
class TestLifecycle : LifecycleOwner {

    /** @return the [LifecycleRegistry.getCurrentState] */
    val currentState get() = registry.currentState

    /** A [LifecycleRegistry] for our [LifecycleOwner] */
    private val registry = LifecycleRegistry(this )

    /** Call the [ON_CREATE] method on [Lifecycle] */
    fun create() =  handleLifecycleEvent( ON_CREATE )
    /** Call the [ON_START] method on [Lifecycle] */
    fun start() =   handleLifecycleEvent( ON_START )
    /** Call the [ON_RESUME] method on [Lifecycle] */
    fun resume() =  handleLifecycleEvent( ON_RESUME )
    /** Call the [ON_PAUSE] method on [Lifecycle] */
    fun pause() =   handleLifecycleEvent( ON_PAUSE )
    /** Call the [ON_STOP] method on [Lifecycle] */
    fun stop() =    handleLifecycleEvent( ON_STOP )
    /** Call the [ON_DESTROY] method on [Lifecycle] */
    fun destroy() = handleLifecycleEvent( ON_DESTROY )

    /** @see LifecycleOwner.getLifecycle */
    override fun getLifecycle() = registry

    /** @see LifecycleRegistry.handleLifecycleEvent */
    private fun handleLifecycleEvent( event: Lifecycle.Event ) = apply {
        registry.handleLifecycleEvent( event )
    }
}