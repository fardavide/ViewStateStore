@file:Suppress("unused")

package studio.forface.viewstatestore

import androidx.annotation.UiThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

/*
 * A set of extension functions
 * Author: Davide Giuseppe Farella
 */

// region factory
/**
 * Create a [ViewStateStore] from a [LiveData]
 *
 * @param liveData [LiveData] of [V] that will handle the main flow of [ViewStateStore]
 * @param [dropOnSame]
 *
 * @see ViewStateStore primary constructor
 *
 * @return [ViewStateStore] of [V]
 */
@Suppress("UNCHECKED_CAST") // LiveData.map function produces a MediatorLiveData, which is subtype of MutableLiveData
fun <V> ViewStateStore.Companion.from( liveData: LiveData<V>, dropOnSame: Boolean = ViewStateStoreConfig.dropOnSame ) =
    ViewStateStore(
        liveData = liveData.map { ViewState( it ) } as MutableLiveData<ViewState<V>>,
        dropOnSame = dropOnSame
    )
// endregion

// region set
/**
 * Set a [ViewState] with the given [state].
 * @see ViewStateStoreScope.setState
 */
@UiThread
fun <V> ViewStateStore<V>.setState( state: ViewState<V>, dropOnSame: Boolean = this.dropOnSame ) {
    setState( state, dropOnSame )
}

/**
 * Set a [ViewState.Success] with the given [data].
 * @see ViewStateStoreScope.setState
 */
@UiThread
fun <V> ViewStateStore<V>.setData( data: V, dropOnSame: Boolean = this.dropOnSame ) {
    setState( ViewState.Success( data ), dropOnSame )
}

/**
 * Set a [ViewState.Error] created from the given [errorThrowable].
 * @see ViewStateStoreScope.setState
 */
@UiThread
fun ViewStateStore<*>.setError(
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
fun ViewStateStore<*>.setLoading( dropOnSame: Boolean = this.dropOnSame ) {
    setState( ViewState.Loading, dropOnSame )
}
// endregion

// region post
/**
 * Post a [ViewState] with the given [state].
 * @see ViewStateStoreScope.postState
 */
fun <V> ViewStateStore<V>.postState( state: ViewState<V>, dropOnSame: Boolean = this.dropOnSame ) {
    postState( state, dropOnSame )
}

/**
 * Post a [ViewState.Success] with the given [data].
 * @see ViewStateStoreScope.postState
 */
fun <V> ViewStateStore<V>.postData( data: V, dropOnSame: Boolean = this.dropOnSame ) {
    postState( ViewState.Success( data ), dropOnSame )
}

/**
 * Post a [ViewState.Error] created from the given [errorThrowable].
 * @see ViewStateStoreScope.postState
 */
fun ViewStateStore<*>.postError(
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
fun ViewStateStore<*>.postLoading( dropOnSame: Boolean = this.dropOnSame ) {
    postState( ViewState.Loading, dropOnSame )
}
// endregion