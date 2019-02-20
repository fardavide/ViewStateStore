@file:Suppress("unused")

package studio.forface.viewstatestore

import androidx.lifecycle.MutableLiveData

/**
 * @author Davide Giuseppe Farella.
 * Base implementation of [AbsViewStateStore]
 *
 * @param initialState the initial [ViewState] to be delivered when [ViewStateStore] is initialized.
 */
class ViewStateStore<V>( initialState: ViewState<V> = ViewState.None ): AbsViewStateStore<V>() {

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