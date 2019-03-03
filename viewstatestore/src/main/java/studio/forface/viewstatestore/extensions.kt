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
fun <V> AbsViewStateStore<V>.postData( data: V, dropOnSame: Boolean = this.dropOnSame ) {
    postState( ViewState.Success( data ), dropOnSame )
}

/**
 * Post a [ViewState.Error] created from the given [errorThrowable].
 * @see ViewStateStore.postState
 */
fun AbsViewStateStore<*>.postError( errorThrowable: Throwable, dropOnSame: Boolean = this.dropOnSame ) {
    postState( ViewState.Error.fromThrowable( errorThrowable ), dropOnSame )
}

/**
 * Post a [ViewState.Loading].
 * @see ViewStateStore.postState
 */
fun AbsViewStateStore<*>.postLoading( dropOnSame: Boolean = this.dropOnSame ) {
    postState( ViewState.Loading, dropOnSame )
}

/**
 * Set a [ViewState.Success] with the given [data].
 * @see ViewStateStore.setState
 */
fun <V> AbsViewStateStore<V>.setData( data: V, dropOnSame: Boolean = this.dropOnSame ) {
    setState( ViewState.Success( data ), dropOnSame )
}

/**
 * Set a [ViewState.Error] created from the given [errorThrowable].
 * @see ViewStateStore.setState
 */
fun AbsViewStateStore<*>.setError( errorThrowable: Throwable, dropOnSame: Boolean = this.dropOnSame ) {
    setState( ViewState.Error.fromThrowable( errorThrowable ), dropOnSame )
}

/**
 * Set a [ViewState.Loading].
 * @see ViewStateStore.postState
 */
fun AbsViewStateStore<*>.setLoading( dropOnSame: Boolean = this.dropOnSame ) {
    setState( ViewState.Loading, dropOnSame )
}