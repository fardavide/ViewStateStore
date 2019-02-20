package studio.forface.viewstatestore.paging

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import studio.forface.viewstatestore.ViewStateObserver

/**
 * @author Davide Giuseppe Farella.
 * A [ViewStateObserver] that supports Android's Paging.
 * It observes a `LiveData` of [V] created by a [DataSource.Factory] of [V] and deliver results to itself
 *
 *
 * @constructor is internal so [PagedViewStateObserver] can be instantiated only from the library.
 * @see PagedViewStateStore.onCreateViewStateObserver
 *
 *
 * @param factory a [DataSource.Factory] as data source of the requested data [V]
 * @see PagedViewStateStore.factory
 *
 * @param pageSize the size of each page of the internal `LiveData`
 * @see PagedViewStateStore.pageSize
 *
 * @param owner the [LifecycleOwner] of the internal `LiveData`
 */
class PagedViewStateObserver<V> internal constructor (
    factory: DataSource.Factory<Int, V>,
    pageSize: Int,
    owner: LifecycleOwner?
): ViewStateObserver<PagedList<V>>() {

    /** A `LiveData` created by [DataSource.Factory] */
    private val liveData = LivePagedListBuilder( factory, pageSize ).build()

    init {
        // Create an Observer that that trigger `onData` when data is received from `liveData`
        val observer = Observer<PagedList<V>> { super.onData( it ) }
        // If `owner` is not null ( `PagedViewStateStore.observe` method call ), invoke `liveData.observe` with
        // the just created `observer`
        // else -if `owner` is null- ( `PagedViewStateObserver.observerForever` method call ), invoke
        // `liveData.observerForever` with the just created `observer`
        owner?.let { liveData.observe( it, observer ) } ?: liveData.observeForever( observer )
    }
}