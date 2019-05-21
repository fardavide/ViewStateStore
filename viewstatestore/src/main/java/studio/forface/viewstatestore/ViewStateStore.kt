@file:Suppress("unused")

package studio.forface.viewstatestore

import androidx.lifecycle.MutableLiveData

/**
 * @author Davide Giuseppe Farella.
 * Base implementation of [AbsViewStateStore]
 *
 *
 * @constructor is internal, use secondary
 *
 *
 * @param liveData [MutableLiveData] of [ViewState] of [V] that overrides [AbsViewStateStore.liveData] that has
 * `initialState` as first `value`
 *
 * @param _initialState the initial [ViewState] to be delivered when [ViewStateStore] is initialized.
 *
 * @param dropOnSame This [Boolean] defines whether a publishing should be dropped if the same [ViewState] is already
 * the last [state]
 * @see ViewStateStoreConfig.dropOnSame
 * Default value is inherited from [ViewStateStoreConfig.dropOnSame]
 */
class ViewStateStore<V> internal constructor(
    override val liveData: MutableLiveData<ViewState<V>> = MutableLiveData<ViewState<V>>().apply {
        value = _initialState
    },
    _initialState: ViewState<V> = ViewState.None, // underscore '_' is needed for don't let it clash with secondary constructor
    dropOnSame: Boolean = ViewStateStoreConfig.dropOnSame
): AbsViewStateStore<V>( dropOnSame ) {

    /**
     * @constructor for implicitly create a [ViewStateStore] with the given [initialData] and [dropOnSame]
     *
     * @param initialData [V] that will be wrapped in [ViewState.Success] and used as `initialState` of the primary
     * constructor
     *
     * @param dropOnSame [Boolean]
     *
     * @see ViewStateStore primary constructor
     */
    constructor( initialData: V, dropOnSame: Boolean = ViewStateStoreConfig.dropOnSame ) :
            this( initialState = ViewState.Success( initialData ), dropOnSame = dropOnSame )

    /**
     * @constructor for implicitly create a [ViewStateStore] with the given [initialState] and [dropOnSame]
     *
     * @param initialState [ViewState] of [V]
     * @param dropOnSame [Boolean]
     *
     * @see ViewStateStore primary constructor
     */
    constructor( initialState: ViewState<V>, dropOnSame: Boolean = ViewStateStoreConfig.dropOnSame ) :
            this( _initialState = initialState, dropOnSame = dropOnSame )
}