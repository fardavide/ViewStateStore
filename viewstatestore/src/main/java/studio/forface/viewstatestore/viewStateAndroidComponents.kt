@file:Suppress("unused")

package studio.forface.viewstatestore

import androidx.lifecycle.LifecycleOwner

/**
 * @author Davide Giuseppe Farella.
 * An interface containing `Lifecycle`-driven observe extensions functions for `Activity`
 *
 * Implements this interface on your `Activity` for be able to call [AbsViewStateStore.observe] and
 * [AbsViewStateStore.observeData] without passing the [LifecycleOwner] explicitly.
 * I.e. `myViewStateStore.observeData( ::updateUi )` it's a valid function that will observe the data respecting the
 * `Activity`s `Lifecycle`.
 */
interface ViewStateActivity: LifecycleOwner {
    /**
     * Call [AbsViewStateStore.observe] within an `Activity` as [LifecycleOwner]
     * This function can't be inlined since it's inside an interface.
     */
    fun <V> AbsViewStateStore<V>.observe( block: ViewStateObserver<V>.() -> Unit  ) =
        observe(this@ViewStateActivity, block )
    /**
     * Call [AbsViewStateStore.observeData] within an `Activity` as [LifecycleOwner]
     * This function can't be inlined since it's inside an interface.
     */
    fun <V> AbsViewStateStore<V>.observeData( block: (V) -> Unit  ) =
        observeData(this@ViewStateActivity, block )
}

/**
 * @author Davide Giuseppe Farella.
 * An interface containing `Lifecycle`-driven observe extensions functions for `Fragment`
 *
 * Implements this interface on your `Fragment` for be able to call [AbsViewStateStore.observe] and
 * [AbsViewStateStore.observeData] without passing the [LifecycleOwner] explicitly.
 * I.e. `myViewStateStore.observeData( ::updateUi )` it's a valid function that will observe the data respecting the
 * `Fragment`s `Lifecycle`.
 */
interface ViewStateFragment {

    /**
     * @return the [LifecycleOwner] of the `Fragment`s `View`.
     * This functions will be automatically overridden if this interface is implemented by a `Fragment`
     */
    fun getViewLifecycleOwner(): LifecycleOwner

    /**
     * Call [AbsViewStateStore.observe] within a [getViewLifecycleOwner] as [LifecycleOwner]
     * This function can't be inlined since it's inside an interface.
     */
    fun <V> AbsViewStateStore<V>.observe( block: ViewStateObserver<V>.() -> Unit  ) =
        observe( getViewLifecycleOwner(), block )
    /**
     * Call [AbsViewStateStore.observeData] within a [getViewLifecycleOwner] as [LifecycleOwner]
     * This function can't be inlined since it's inside an interface.
     */
    fun <V> AbsViewStateStore<V>.observeData( block: (V) -> Unit  ) =
        observeData( getViewLifecycleOwner(), block )
}