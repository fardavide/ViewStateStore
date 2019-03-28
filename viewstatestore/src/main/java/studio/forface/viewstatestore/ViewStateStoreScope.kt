package studio.forface.viewstatestore

import androidx.lifecycle.MutableLiveData

/**
 * Only entities that implements this interface will be able to call `set` and `post` methods of [LockedViewStateStore],
 * This is also implemented by [AbsViewStateStore]
 *
 * @author Davide Giuseppe Farella
 */
interface ViewStateStoreScope {

    /**
     * @see MutableLiveData.postValue on [LockedViewStateStore.liveData]
     *
     * @param dropOnSame This [Boolean] defines whether a publishing should be dropped if the same [ViewState] is
     * same as the last [state]
     * Default is [ViewStateStore.dropOnSame]
     */
    fun <V> LockedViewStateStore<V>.postState( state: ViewState<V>, dropOnSame: Boolean = this.dropOnSame ) {
        if ( ! dropOnSame || state() != state )
            liveData.postValue( state )
    }

    /**
     * @see MutableLiveData.setValue of [LockedViewStateStore.liveData]
     *
     * @param dropOnSame This [Boolean] defines whether a publishing should be dropped if the same [ViewState] is
     * same as the last [state]
     * Default is [ViewStateStore.dropOnSame]
     */
    fun <V> LockedViewStateStore<V>.setState( state: ViewState<V>, dropOnSame: Boolean = this.dropOnSame ) {
        if ( ! dropOnSame || state() != state )
            liveData.value = state
    }

    /* === EXTENSIONS === */

    /**
     * Post a [ViewState.Success] with the given [data].
     * @see postState
     */
    fun <V> LockedViewStateStore<V>.postData( data: V, dropOnSame: Boolean = this.dropOnSame ) {
        postState( ViewState.Success( data ), dropOnSame )
    }

    /**
     * Post a [ViewState.Error] created from the given [errorThrowable].
     * @see postState
     */
    fun LockedViewStateStore<*>.postError( errorThrowable: Throwable, dropOnSame: Boolean = this.dropOnSame ) {
        postState( ViewState.Error.fromThrowable( errorThrowable ), dropOnSame )
    }

    /**
     * Post a [ViewState.Loading].
     * @see postState
     */
    fun LockedViewStateStore<*>.postLoading( dropOnSame: Boolean = this.dropOnSame ) {
        postState( ViewState.Loading, dropOnSame )
    }

    /**
     * Set a [ViewState.Success] with the given [data].
     * @see setState
     */
    fun <V> LockedViewStateStore<V>.setData( data: V, dropOnSame: Boolean = this.dropOnSame ) {
        setState( ViewState.Success( data ), dropOnSame )
    }

    /**
     * Set a [ViewState.Error] created from the given [errorThrowable].
     * @see setState
     */
    fun LockedViewStateStore<*>.setError( errorThrowable: Throwable, dropOnSame: Boolean = this.dropOnSame ) {
        setState( ViewState.Error.fromThrowable( errorThrowable ), dropOnSame )
    }

    /**
     * Set a [ViewState.Loading].
     * @see postState
     */
    fun LockedViewStateStore<*>.setLoading( dropOnSame: Boolean = this.dropOnSame ) {
        setState( ViewState.Loading, dropOnSame )
    }
}