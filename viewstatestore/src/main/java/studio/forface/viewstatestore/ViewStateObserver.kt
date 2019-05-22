@file:Suppress("unused")

package studio.forface.viewstatestore

/**
 * @author Davide Giuseppe Farella.
 * An observer for [ViewState].
 *
 * It will be returned as lambda receiver of [ViewStateStore.observe] and [ViewStateStore.observeForever].
 */
open class ViewStateObserver<V> {

    /** A callback that will be triggered on [ViewState.doOnData] */
    @PublishedApi internal var onData: ( (V) -> Unit ) = {}

    /** A callback that will be triggered on each [ViewState] */
    @PublishedApi internal var onEach: ( (ViewState<V>) -> Unit ) = {}

    /** A callback that will be triggered on [ViewState.doOnError] */
    @PublishedApi internal var onError: ( (ViewState.Error) -> Unit ) = {}

    /** A callback that will be triggered on [ViewState.doOnLoadingChange] */
    @PublishedApi internal var onLoadingChange: ( (Boolean) -> Unit ) = {}

    /** Run [action] for every event */
    fun doOnEach( action: (ViewState<V>) -> Unit ) = apply { onEach = action }

    /** Run [action] when data is received correctly */
    fun doOnData( action: (V) -> Unit ) = apply { onData = action }

    /** Run [action] when an error occurs */
    fun doOnError( action: (ViewState.Error) -> Unit ) = apply { onError = action }

    /** Run [action] when the loading state changes */
    fun doOnLoadingChange( action: (Boolean) -> Unit ) = apply { onLoadingChange = action }
}