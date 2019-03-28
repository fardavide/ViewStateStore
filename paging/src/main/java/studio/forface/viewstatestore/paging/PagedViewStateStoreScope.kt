@file:Suppress("unused")

package studio.forface.viewstatestore.paging

import androidx.annotation.UiThread
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import studio.forface.viewstatestore.ViewStateStoreScope

/**
 * Only entities that implements this interface will be able to call `set` and `post` methods of
 * [LockedPagedViewStateStore],
 * This is also implemented by [PagedViewStateStore]
 *
 * @author Davide Giuseppe Farella
 */
interface PagedViewStateStoreScope : ViewStateStoreScope {

    /**
     * Set the [DataSource.Factory] as data source of the requested data [V] for the receiver
     * [LockedPagedViewStateStore]
     */
    @UiThread
    fun <V> LockedPagedViewStateStore<V>.setDataSource( factory: DataSource.Factory<Int, V> ) {
        pagedLiveData = LivePagedListBuilder( factory, pageSize ).build()
    }
}

/** Typealias for [PagedViewStateStoreScope] */
typealias ViewStateStoreScope = PagedViewStateStoreScope