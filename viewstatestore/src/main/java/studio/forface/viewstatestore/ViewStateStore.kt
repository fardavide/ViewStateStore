@file:Suppress("unused")

package studio.forface.viewstatestore

import androidx.lifecycle.MutableLiveData

/**
 * @author Davide Giuseppe Farella.
 * Base implementation of [AbsViewStateStore]
 *
 * @param initialState the initial [ViewState] to be delivered when [ViewStateStore] is initialized.
 *
 * @param dropOnSame This [Boolean] defines whether a publishing should be dropped if the same [ViewState] is already
 * the last [state]
 * @see ViewStateStoreConfig.dropOnSame
 * Default value is inherited from [ViewStateStoreConfig.dropOnSame]
 */
class ViewStateStore<V>(
    initialState: ViewState<V> = ViewState.None,
    dropOnSame: Boolean = ViewStateStoreConfig.dropOnSame
): AbsViewStateStore<V>( dropOnSame ) {

    /**
     * @constructor for implicitly create a [ViewStateStore] with a [ViewState.Success] as `initialState` with the
     * given [data]
     */
    constructor( data: V ): this( ViewState.Success( data ) )

    /** @override of [AbsViewStateStore.liveData] that has `initialState` as first `value` */
    override val liveData = MutableLiveData<ViewState<V>>().apply {
        value = initialState
    }
}