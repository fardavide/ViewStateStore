@file:Suppress("MemberVisibilityCanBePrivate", "unused")

package studio.forface.viewstatestore.paging

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import studio.forface.viewstatestore.LockedViewStateStore
import studio.forface.viewstatestore.ViewState
import studio.forface.viewstatestore.ViewStateStoreConfig

/**
 * A Locked `ViewStateStore` that support Android's Paging
 *
 * Inherit from [LockedViewStateStore]
 *
 * Use [PagedViewStateStoreScope.setDataSource] for generate your data [V] instead of
 * [PagedViewStateStoreScope.setData], like you would do a "classic" `ViewStateStore`,
 *
 *
 * @param pageSize the size of the page of the internal [pagedLiveData].
 * @see LivePagedListBuilder constructor.
 * Default is 25
 *
 * @param dropOnSame This [Boolean] defines whether a publishing should be dropped if the same [ViewState] is already
 * the last [state]
 * @see ViewStateStoreConfig.dropOnSame
 * Default value is inherited from [ViewStateStoreConfig.dropOnSame]
 *
 *
 * @author Davide Giuseppe Farella
 */
abstract class LockedPagedViewStateStore<V>(
    internal var pageSize: Int = 25,
    internal val dropOnSame: Boolean = ViewStateStoreConfig.dropOnSame
) : LockedViewStateStore<PagedList<V>>( dropOnSame ) {

    /** A `LiveData` created by [DataSource.Factory] */
    // TODO make abstract / move to constructor in 1.4
    internal lateinit var pagedLiveData: LiveData<PagedList<V>>

    /**
     * @return a new instance of [PagedViewStateObserver]
     * Also start observing [pagedLiveData] with the given [LifecycleOwner], which its [Observer] will call
     * [handleViewState]
     */
    override fun onCreateViewStateObserver( owner: LifecycleOwner? ): PagedViewStateObserver<V> {
        val viewStateObserver = PagedViewStateObserver<V>()

        // Create an Observer that that trigger `onData` when data is received from `liveData`
        val pagedObserver = Observer<PagedList<V>> { handleViewState( viewStateObserver, ViewState( it ) ) }
        // If `owner` is not null ( `PagedViewStateStore.observe` method call ), invoke `liveData.observe` with
        // the just created `observer`
        // else -if `owner` is null- ( `PagedViewStateObserver.observerForever` method call ), invoke
        // `liveData.observerForever` with the just created `observer`
        if ( owner != null )
            owner.let { pagedLiveData.observe( it, pagedObserver ) }
        else pagedLiveData.observeForever( pagedObserver )

        return viewStateObserver
    }
}