@file:Suppress("unused")

package studio.forface.viewstatestore.paging

import androidx.annotation.UiThread
import androidx.paging.DataSource
import androidx.paging.PagedList
import studio.forface.viewstatestore.ErrorResolution
import studio.forface.viewstatestore.ViewState
import studio.forface.viewstatestore.ViewStateStore
import studio.forface.viewstatestore.ViewStateStoreConfig

/*
 * A set of extension functions
 * Author: Davide Giuseppe Farella
 */

// region factory
fun <V> ViewStateStore.Companion.from(
    dataSourceFactory: DataSource.Factory<Int, V>,
    pageSize: Int = PagedViewStateStore.DEFAULT_PAGE_SIZE,
    dropOnSame: Boolean = ViewStateStoreConfig.dropOnSame
) = PagedViewStateStore(dataSourceFactory, pageSize, dropOnSame)
// endregion

// region set
/**
 * Set a [ViewState] with the given [state].
 * @see PagedViewStateStoreScope.setState
 */
@UiThread
fun <V> PagedViewStateStore<V>.set(
    state: ViewState<PagedList<V>>,
    dropOnSame: Boolean = this.dropOnSame
) {
    setState(state, dropOnSame)
}
/**
 * Set a [ViewState] with the given [state].
 * @see PagedViewStateStoreScope.setState
 */
@UiThread
fun <V> PagedViewStateStore<V>.setState(
    state: ViewState<PagedList<V>>,
    dropOnSame: Boolean = this.dropOnSame
) {
    setState(state, dropOnSame)
}

/**
 * Set the [DataSource.Factory] as data source of the requested data [V] for the receiver [PagedViewStateStore]
 * @see PagedViewStateStoreScope.setDataSource
 */
// TODO remove in 1.4
@Deprecated(
    "Use ViewStateStore.from for initialize with a DataSource.Factory. This will be removed in 1.4",
    ReplaceWith(
        "ViewStateStore.from(factory)",
        "studio.forface.viewstatestore.paging.ViewStateStore",
        "studio.forface.viewstatestore.paging.from"
    )
)
@UiThread
fun <V> PagedViewStateStore<V>.setDataSource(factory: DataSource.Factory<Int, V>) {
    setDataSource(factory)
}

/**
 * Set a [ViewState.Success] with the given [data].
 * @see PagedViewStateStoreScope.setState
 */
@UiThread
fun <V> PagedViewStateStore<V>.set(data: PagedList<V>, dropOnSame: Boolean = this.dropOnSame) {
    setData(data, dropOnSame)
}

/**
 * Set a [ViewState.Success] with the given [data].
 * @see PagedViewStateStoreScope.setState
 */
@UiThread
fun <V> PagedViewStateStore<V>.setData(data: PagedList<V>, dropOnSame: Boolean = this.dropOnSame) {
    setState(ViewState.Success(data), dropOnSame)
}

/**
 * Set a [ViewState.Error] created from the given [errorThrowable].
 * @see PagedViewStateStoreScope.setState
 */
@UiThread
fun PagedViewStateStore<*>.setError(
    errorThrowable: Throwable,
    dropOnSame: Boolean = this.dropOnSame,
    errorResolution: ErrorResolution? = null
) {
    setState(ViewState.Error.fromThrowable(errorThrowable, errorResolution), dropOnSame)
}

/**
 * Set a [ViewState.Loading].
 * @see PagedViewStateStoreScope.postState
 */
@UiThread
fun PagedViewStateStore<*>.setLoading(dropOnSame: Boolean = this.dropOnSame) {
    setState(ViewState.Loading, dropOnSame)
}
// endregion

// region post
/**
 * Post a [ViewState] with the given [state].
 * @see PagedViewStateStoreScope.postState
 */
fun <V> PagedViewStateStore<V>.post(
    state: ViewState<PagedList<V>>,
    dropOnSame: Boolean = this.dropOnSame
) {
    postState(state, dropOnSame)
}
/**
 * Post a [ViewState] with the given [state].
 * @see PagedViewStateStoreScope.postState
 */
fun <V> PagedViewStateStore<V>.postState(
    state: ViewState<PagedList<V>>,
    dropOnSame: Boolean = this.dropOnSame
) {
    postState(state, dropOnSame)
}

/**
 * Post a [ViewState.Success] with the given [data].
 * @see PagedViewStateStoreScope.postState
 */
fun <V> PagedViewStateStore<V>.post(data: PagedList<V>, dropOnSame: Boolean = this.dropOnSame) {
    postData(data, dropOnSame)
}

/**
 * Post a [ViewState.Success] with the given [data].
 * @see PagedViewStateStoreScope.postState
 */
fun <V> PagedViewStateStore<V>.postData(data: PagedList<V>, dropOnSame: Boolean = this.dropOnSame) {
    postState(ViewState.Success(data), dropOnSame)
}

/**
 * Post a [ViewState.Error] created from the given [errorThrowable].
 * @see PagedViewStateStoreScope.postState
 */
fun PagedViewStateStore<*>.postError(
    errorThrowable: Throwable,
    dropOnSame: Boolean = this.dropOnSame,
    errorResolution: ErrorResolution? = null
) {
    postState(ViewState.Error.fromThrowable(errorThrowable, errorResolution), dropOnSame)
}

/**
 * Post a [ViewState.Loading].
 * @see PagedViewStateStoreScope.postState
 */
fun PagedViewStateStore<*>.postLoading(dropOnSame: Boolean = this.dropOnSame) {
    postState(ViewState.Loading, dropOnSame)
}
// endregion
