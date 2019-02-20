@file:Suppress("unused")

package studio.forface.viewstatestore

/**
 * @author Davide Giuseppe Farella.
 * An observer for [ViewState].
 *
 * It will be returned as lambda receiver of [AbsViewStateStore.observe] and [AbsViewStateStore.observeForever].
 */
open class ViewStateObserver<V> {

    /** A callback that will be triggered on [ViewState.doOnData] */
    var onData: ( (V) -> Unit ) = {}

    /** A callback that will be triggered on each [ViewState] */
    var onEach: ( (ViewState<V>) -> Unit ) = {}

    /** A callback that will be triggered on [ViewState.doOnError] */
    var onError: ( (ViewState.Error) -> Unit ) = {}

    /** A callback that will be triggered on [ViewState.doOnLoadingChange] */
    var onLoadingChange: ( (Boolean) -> Unit ) = {}

    /** Run [action] for every event */
    fun doOnEach( action: (ViewState<V>) -> Unit ) = apply { onEach = action }

    /** Run [action] when data is received correctly */
    fun doOnData( action: (V) -> Unit ) = apply { onData = action }

    /** Run [action] when an error occurs */
    fun doOnError( action: (ViewState.Error) -> Unit ) = apply { onError = action }

    /** Run [action] when the loading state changes */
    fun doOnLoadingChange( action: (Boolean) -> Unit ) = apply { onLoadingChange = action }
}