@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package studio.forface.viewstatestore

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData

/**
 * This class will store and handle the [ViewState] and submit it via a [LiveData].
 * `ViewStateStore` will only deliver the last [data] when the observer become active.
 *
 * Inherit from [LockedViewStateStore] and implements [ViewStateStoreScope], so [setState] and [postState] can be called
 * without any additional scope.
 *
 *
 * ### Publish:
 *
 * Use the [setState] for deliver the [ViewState] on the current thread.
 * Use the [postState] for deliver the [ViewState] on the main thread.
 * Use [state] for retrieve the last state.
 *
 *
 * ### Observe:
 *
 * Use [observe] for observe the published [ViewState] within a [LifecycleOwner]
 * Use [observeForever] for observe the published [ViewState]. NOTE! This will probably lead to a memory leak.
 *
 * Use [observeData] for observe ONLY the the published [data] within a [LifecycleOwner]
 * Use [observeForever] for observe ONLY the the published [data]. NOTE! This will probably lead to a memory leak.
 *
 * @see ViewStateObserver
 *
 *
 * This class is abstract and will be inherited from `ViewStateStore` and `PagedViewStateStore`.
 *
 * @param V is the type of the [data]
 *
 * @param dropOnSame This [Boolean] defines whether a publishing should be dropped if the same [ViewState] is already
 * the last [state]
 * @see ViewStateStoreConfig.dropOnSame
 * Default value is inherited from [ViewStateStoreConfig.dropOnSame]
 *
 *
 * @author Davide Giuseppe Farella
 */
abstract class AbsViewStateStore<V>( dropOnSame: Boolean ) :
    LockedViewStateStore<V>( dropOnSame ),
    ViewStateStoreScope {

    /**
     * @return [LockedViewStateStore] obtained by casting `this` instance.
     * Use this for forbid access to `set` and `post` functions without a [ViewStateStoreScope]
     */
    val lock: LockedViewStateStore<V> get() = this
}