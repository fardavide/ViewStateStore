@file:Suppress("unused")

package studio.forface.viewstatestore

/*
 * Author: Davide Giuseppe Farella
 * A set of extension functions
 */

/**
 * Post a [ViewState.Success] with the given [data].
 * @see ViewStateStore.postState
 */
fun <V> AbsViewStateStore<V>.postData( data: V ) {
    postState( ViewState.Success( data ) )
}

/**
 * Post a [ViewState.Error] created from the given [errorThrowable].
 * @see ViewStateStore.postState
 */
fun AbsViewStateStore<*>.postError( errorThrowable: Throwable ) {
    postState( ViewState.Error.fromThrowable( errorThrowable ) )
}

/**
 * Post a [ViewState.Loading].
 * @see ViewStateStore.postState
 */
fun AbsViewStateStore<*>.postLoading() {
    postState( ViewState.Loading )
}

/**
 * Set a [ViewState.Success] with the given [data].
 * @see ViewStateStore.setState
 */
fun <V> AbsViewStateStore<V>.setData( data: V ) {
    setState( ViewState.Success( data ) )
}

/**
 * Set a [ViewState.Error] created from the given [errorThrowable].
 * @see ViewStateStore.setState
 */
fun AbsViewStateStore<*>.setError( errorThrowable: Throwable ) {
    setState( ViewState.Error.fromThrowable( errorThrowable ) )
}

/**
 * Set a [ViewState.Loading].
 * @see ViewStateStore.postState
 */
fun AbsViewStateStore<*>.setLoading() {
    setState( ViewState.Loading )
}