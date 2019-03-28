@file:Suppress("unused")

package studio.forface.viewstatestore.paging

import androidx.annotation.UiThread
import androidx.paging.DataSource
import androidx.paging.PagedList
import studio.forface.viewstatestore.ViewState

/*
 * Author: Davide Giuseppe Farella
 * A set of extension functions
 */

/**
 * Set a [ViewState] with the given [state].
 * @see PagedViewStateStoreScope.setState
 */
@UiThread
fun <V> PagedViewStateStore<V>.setState( state: ViewState<PagedList<V>>, dropOnSame: Boolean = this.dropOnSame ) {
    setState( state, dropOnSame )
}

/**
 * Set the [DataSource.Factory] as data source of the requested data [V] for the receiver [PagedViewStateStore]
 * @see PagedViewStateStoreScope.setDataSource
 */
@UiThread
fun <V> PagedViewStateStore<V>.setDataSource( factory: DataSource.Factory<Int, V> ) {
    setDataSource( factory )
}

/**
 * Set a [ViewState.Success] with the given [data].
 * @see PagedViewStateStoreScope.setState
 */
@UiThread
fun <V> PagedViewStateStore<V>.setData( data: PagedList<V>, dropOnSame: Boolean = this.dropOnSame ) {
    setState( ViewState.Success( data ), dropOnSame )
}

/**
 * Set a [ViewState.Error] created from the given [errorThrowable].
 * @see PagedViewStateStoreScope.setState
 */
@UiThread
fun PagedViewStateStore<*>.setError( errorThrowable: Throwable, dropOnSame: Boolean = this.dropOnSame ) {
    setState( ViewState.Error.fromThrowable( errorThrowable ), dropOnSame )
}

/**
 * Set a [ViewState.Loading].
 * @see PagedViewStateStoreScope.postState
 */
@UiThread
fun PagedViewStateStore<*>.setLoading( dropOnSame: Boolean = this.dropOnSame ) {
    setState( ViewState.Loading, dropOnSame )
}

/**
 * Post a [ViewState] with the given [state].
 * @see PagedViewStateStoreScope.postState
 */
fun <V> PagedViewStateStore<V>.postState( state: ViewState<PagedList<V>>, dropOnSame: Boolean = this.dropOnSame ) {
    postState( state, dropOnSame )
}

/**
 * Post a [ViewState.Success] with the given [data].
 * @see PagedViewStateStoreScope.postState
 */
fun <V> PagedViewStateStore<V>.postData( data: PagedList<V>, dropOnSame: Boolean = this.dropOnSame ) {
    postState( ViewState.Success( data ), dropOnSame )
}

/**
 * Post a [ViewState.Error] created from the given [errorThrowable].
 * @see PagedViewStateStoreScope.postState
 */
fun PagedViewStateStore<*>.postError( errorThrowable: Throwable, dropOnSame: Boolean = this.dropOnSame ) {
    postState( ViewState.Error.fromThrowable( errorThrowable ), dropOnSame )
}

/**
 * Post a [ViewState.Loading].
 * @see PagedViewStateStoreScope.postState
 */
fun PagedViewStateStore<*>.postLoading( dropOnSame: Boolean = this.dropOnSame ) {
    postState( ViewState.Loading, dropOnSame )
}
