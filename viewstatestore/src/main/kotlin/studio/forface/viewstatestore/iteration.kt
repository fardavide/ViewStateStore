package studio.forface.viewstatestore

import androidx.lifecycle.LifecycleOwner

/**
 * Iterator that suspends its functions.
 * Instances of this interface are *not thread-safe* and shall not be used from concurrent
 * coroutines.
 *
 * @author Davide Farella
 */
interface SuspendIterator<out V> {
    /**
     * Returns `true` if the [LifecycleOwner.isAlive], suspending the caller while
     * [LifecycleOwner.isInactive], or returns `false` if [LifecycleOwner.isNotAlive].
     */
    suspend operator fun hasNext() : Boolean

    /**
     * Retrieves the element next available element.
     * [next] should only be used in pair with [hasNext]:
     * ```
     * while (iterator.hasNext()) {
     *     val element = iterator.next()
     *     // ... handle element ...
     * }
     * ```
     */
    suspend operator fun next() : V
}
