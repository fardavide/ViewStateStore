@file:Suppress("unused")

package studio.forface.viewstatestore

import androidx.annotation.UiThread

/*
 * Author: Davide Giuseppe Farella
 * A set of extension functions
 */

// region set
/**
 * Set a [ViewState] with the given [state].
 * @see ViewStateStoreScope.setState
 */
@UiThread
fun <V> AbsViewStateStore<V>.setState( state: ViewState<V>, dropOnSame: Boolean = this.dropOnSame ) {
    setState( state, dropOnSame )
}

/**
 * Set a [ViewState.Success] with the given [data].
 * @see ViewStateStoreScope.setState
 */
@UiThread
fun <V> AbsViewStateStore<V>.setData( data: V, dropOnSame: Boolean = this.dropOnSame ) {
    setState( ViewState.Success( data ), dropOnSame )
}

/**
 * Set a [ViewState.Error] created from the given [errorThrowable].
 * @see ViewStateStoreScope.setState
 */
@UiThread
fun AbsViewStateStore<*>.setError(
    errorThrowable: Throwable,
    dropOnSame: Boolean = this.dropOnSame,
    errorResolution: ErrorResolution? = null
) {
    setState( ViewState.Error.fromThrowable( errorThrowable, errorResolution ), dropOnSame )
}

/**
 * Set a [ViewState.Loading].
 * @see ViewStateStoreScope.postState
 */
@UiThread
fun AbsViewStateStore<*>.setLoading( dropOnSame: Boolean = this.dropOnSame ) {
    setState( ViewState.Loading, dropOnSame )
}
// endregion

// region post
/**
 * Post a [ViewState] with the given [state].
 * @see ViewStateStoreScope.postState
 */
fun <V> AbsViewStateStore<V>.postState( state: ViewState<V>, dropOnSame: Boolean = this.dropOnSame ) {
    postState( state, dropOnSame )
}

/**
 * Post a [ViewState.Success] with the given [data].
 * @see ViewStateStoreScope.postState
 */
fun <V> AbsViewStateStore<V>.postData( data: V, dropOnSame: Boolean = this.dropOnSame ) {
    postState( ViewState.Success( data ), dropOnSame )
}

/**
 * Post a [ViewState.Error] created from the given [errorThrowable].
 * @see ViewStateStoreScope.postState
 */
fun AbsViewStateStore<*>.postError(
    errorThrowable: Throwable,
    dropOnSame: Boolean = this.dropOnSame,
    errorResolution: ErrorResolution? = null
) {
    postState( ViewState.Error.fromThrowable( errorThrowable, errorResolution ), dropOnSame )
}

/**
 * Post a [ViewState.Loading].
 * @see ViewStateStoreScope.postState
 */
fun AbsViewStateStore<*>.postLoading( dropOnSame: Boolean = this.dropOnSame ) {
    postState( ViewState.Loading, dropOnSame )
}
// endregion