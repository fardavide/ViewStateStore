package studio.forface.viewstatestore.paging

import androidx.paging.DataSource
import androidx.paging.PagedList
import studio.forface.viewstatestore.ViewStateObserver

/**
 * @author Davide Giuseppe Farella.
 * A [ViewStateObserver] that supports Android's Paging.
 * It observes a `LiveData` of [V] created by a [DataSource.Factory] of [V] and deliver results to itself
 *
 * @constructor is internal so [PagedViewStateObserver] can be instantiated only from the library.
 * @see PagedViewStateStore.onCreateViewStateObserver
 */
class PagedViewStateObserver<V> internal constructor() : ViewStateObserver<PagedList<V>>()