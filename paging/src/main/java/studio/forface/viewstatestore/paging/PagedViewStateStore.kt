@file:Suppress("MemberVisibilityCanBePrivate", "unused")

package studio.forface.viewstatestore.paging

import androidx.annotation.UiThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import studio.forface.viewstatestore.AbsViewStateStore
import studio.forface.viewstatestore.ViewState
import studio.forface.viewstatestore.ViewStateStoreConfig
import studio.forface.viewstatestore.setData

/**
 * @author Davide Giuseppe Farella.
 * An [AbsViewStateStore] that support Android's Paging
 *
 * Use [setDataSource] for generate your data [V]
 * instead of [setData], like you would do a "classic" `ViewStateStore`,
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
 */
class PagedViewStateStore<V>(
    private var pageSize: Int = 25,
    dropOnSame: Boolean = ViewStateStoreConfig.dropOnSame
) : AbsViewStateStore<PagedList<V>>( dropOnSame ) {

    /** A `LiveData` created by [DataSource.Factory] */
    private lateinit var pagedLiveData: LiveData<PagedList<V>>

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

    /** Set the [DataSource.Factory] as data source of the requested data [V] */
    @UiThread
    fun setDataSource( factory: DataSource.Factory<Int, V> ) {
        pagedLiveData = LivePagedListBuilder( factory, pageSize ).build()
    }
}