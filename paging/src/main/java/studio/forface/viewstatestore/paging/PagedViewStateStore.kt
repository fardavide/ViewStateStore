@file:Suppress("MemberVisibilityCanBePrivate", "unused")

package studio.forface.viewstatestore.paging

import androidx.annotation.UiThread
import androidx.lifecycle.LifecycleOwner
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
 * @param pageSize the size of the page of the internal [PagedViewStateObserver.liveData].
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

    /** A [DataSource.Factory] for retrieve [V] */
    private lateinit var factory: DataSource.Factory<Int, V>

    /** @return a new instance of [PagedViewStateObserver] */
    override fun onCreateViewStateObserver( owner: LifecycleOwner? ) =
            PagedViewStateObserver( factory, pageSize, owner )

    /** Set the [DataSource.Factory] as data source of the requested data [V] */
    @UiThread
    fun setDataSource( factory: DataSource.Factory<Int, V> ) {
        this.factory = factory
    }
}