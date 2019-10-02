package studio.forface.viewstatestore

import androidx.lifecycle.MutableLiveData

/**
 * Only entities that implements this interface will be able to call `set` and `post` methods of [LockedViewStateStore],
 * This is also implemented by [ViewStateStore]
 *
 * @author Davide Giuseppe Farella
 */
interface ViewStateStoreScope {

    /**
     * @see MutableLiveData.setValue of [LockedViewStateStore.liveData]
     *
     * @param dropOnSame This [Boolean] defines whether a publishing should be dropped if the same [ViewState] is
     * same as the last [state]
     * Default is [ViewStateStore.dropOnSame]
     */
    fun <V> LockedViewStateStore<V>.set(
        state: ViewState<V>,
        dropOnSame: Boolean = this.dropOnSame
    ) {
        setState(state, dropOnSame)
    }

    /**
     * @see MutableLiveData.setValue of [LockedViewStateStore.liveData]
     *
     * @param dropOnSame This [Boolean] defines whether a publishing should be dropped if the same [ViewState] is
     * same as the last [state]
     * Default is [ViewStateStore.dropOnSame]
     */
    fun <V> LockedViewStateStore<V>.setState(
        state: ViewState<V>,
        dropOnSame: Boolean = this.dropOnSame
    ) {
        if (!dropOnSame || state() != state)
            liveData.value = state
    }

    /**
     * @see MutableLiveData.postValue on [LockedViewStateStore.liveData]
     *
     * @param dropOnSame This [Boolean] defines whether a publishing should be dropped if the same [ViewState] is
     * same as the last [state]
     * Default is [ViewStateStore.dropOnSame]
     */
    fun <V> LockedViewStateStore<V>.post(
        state: ViewState<V>,
        dropOnSame: Boolean = this.dropOnSame
    ) {
        postState(state, dropOnSame)
    }

    /**
     * @see MutableLiveData.postValue on [LockedViewStateStore.liveData]
     *
     * @param dropOnSame This [Boolean] defines whether a publishing should be dropped if the same [ViewState] is
     * same as the last [state]
     * Default is [ViewStateStore.dropOnSame]
     */
    fun <V> LockedViewStateStore<V>.postState(
        state: ViewState<V>,
        dropOnSame: Boolean = this.dropOnSame
    ) {
        if (!dropOnSame || state() != state)
            liveData.postValue(state)
    }

    // region Extensions

    /**
     * Set a [ViewState.Success] with the given [data].
     * @see setState
     */
    fun <V> LockedViewStateStore<V>.set(data: V, dropOnSame: Boolean = this.dropOnSame) {
        setData(data, dropOnSame)
    }

    /**
     * Set a [ViewState.Success] with the given [data].
     * @see setState
     */
    fun <V> LockedViewStateStore<V>.setData(data: V, dropOnSame: Boolean = this.dropOnSame) {
        setState(ViewState.Success(data), dropOnSame)
    }

    /**
     * Post a [ViewState.Success] with the given [data].
     * @see postState
     */
    fun <V> LockedViewStateStore<V>.post(data: V, dropOnSame: Boolean = this.dropOnSame) {
        postData(data, dropOnSame)
    }

    /**
     * Post a [ViewState.Success] with the given [data].
     * @see postState
     */
    fun <V> LockedViewStateStore<V>.postData(data: V, dropOnSame: Boolean = this.dropOnSame) {
        postState(ViewState.Success(data), dropOnSame)
    }

    /**
     * Set a [ViewState.Error] created from the given [errorThrowable].
     * @see setState
     */
    fun LockedViewStateStore<*>.setError(
        errorThrowable: Throwable,
        dropOnSame: Boolean = this.dropOnSame,
        errorResolution: ErrorResolution? = null
    ) {
        setState(ViewState.Error.fromThrowable(errorThrowable, errorResolution), dropOnSame)
    }

    /**
     * Post a [ViewState.Error] created from the given [errorThrowable].
     * @see postState
     */
    fun LockedViewStateStore<*>.postError(
        errorThrowable: Throwable,
        dropOnSame: Boolean = this.dropOnSame,
        errorResolution: ErrorResolution? = null
    ) {
        postState(ViewState.Error.fromThrowable(errorThrowable, errorResolution), dropOnSame)
    }

    /**
     * Set a [ViewState.Loading].
     * @see postState
     */
    fun LockedViewStateStore<*>.setLoading(dropOnSame: Boolean = this.dropOnSame) {
        setState(ViewState.Loading, dropOnSame)
    }

    /**
     * Post a [ViewState.Loading].
     * @see postState
     */
    fun LockedViewStateStore<*>.postLoading(dropOnSame: Boolean = this.dropOnSame) {
        postState(ViewState.Loading, dropOnSame)
    }

    // endregion
}
