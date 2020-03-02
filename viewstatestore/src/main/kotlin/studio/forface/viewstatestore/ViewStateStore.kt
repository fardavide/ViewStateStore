@file:Suppress("unused")

package studio.forface.viewstatestore

import androidx.lifecycle.MutableLiveData

/**
 * This class will store and handle the [ViewState] and submit it via a `LiveData`.
 * `ViewStateStore` will only deliver the last [data] when the observer become active.
 *
 * Inherit from [LockedViewStateStore] and implements [ViewStateStoreScope], so [setState] and [postState] can be called
 * without any additional scope.
 *
 *
 * ### Publish:
 *
 * Use the [setState] for deliver the [ViewState] on the current thread.
 * Use the [postState] for deliver the [ViewState] on the main thread.
 * Use [state] for retrieve the last state.
 *
 *
 * ### Observe:
 *
 * Use [observe] for observe the published [ViewState] within a `LifecycleOwner`
 * Use [observeForever] for observe the published [ViewState]. NOTE! This will probably lead to a memory leak. Use for
 * testing only!
 *
 * Use [observeData] for observe ONLY the the published [data] within a `LifecycleOwner`
 * Use [observeForever] for observe ONLY the the published [data]. NOTE! This will probably lead to a memory leak. Use
 * for testing only!
 *
 * @see ViewStateObserver
 *
 *
 * @constructor is internal, use secondary
 *
 *
 * @param liveData [MutableLiveData] of [ViewState] of [V] that overrides [LockedViewStateStore.liveData] that has
 * `initialState` as first `value`
 *
 * @param _initialState the initial [ViewState] to be delivered when [ViewStateStore] is initialized.
 *
 * @param dropOnSame This [Boolean] defines whether a publishing should be dropped if the same [ViewState] is already
 * the last [state]
 *
 *
 * @author Davide Giuseppe Farella
 */
class ViewStateStore<V> @Suppress("ConstructorParameterNaming") internal constructor(
    override val liveData: MutableLiveData<ViewState<V>> = MutableLiveData<ViewState<V>>().apply {
        value = _initialState
    },
    // underscore '_' is needed for don't let it clash with secondary constructor
    _initialState: ViewState<V> = ViewState.None,
    dropOnSame: Boolean
) : LockedViewStateStore<V>(dropOnSame), ViewStateStoreScope {

    /**
     * @return [LockedViewStateStore] obtained by casting `this` instance.
     * Use this for forbid access to `set` and `post` functions without a [ViewStateStoreScope]
     */
    val lock: LockedViewStateStore<V> get() = this

    /**
     * @constructor for create a [ViewStateStore] with the given [initialState] and [dropOnSame]
     *
     * @param initialState [ViewState] of [V]
     *
     * @param dropOnSame [Boolean]
     * Default value is inherited from [ViewStateStoreConfig.dropOnSame]
     *
     * @see ViewStateStore primary constructor
     */
    constructor(
        initialState: ViewState<V> = ViewState.None,
        dropOnSame: Boolean = ViewStateStoreConfig.dropOnSame
    ) : this(_initialState = initialState, dropOnSame = dropOnSame)

    /**
     * @constructor for create a [ViewStateStore] with the given [initialData] and [dropOnSame]
     *
     * @param initialData [V] that will be wrapped in [ViewState.Success] and used as `initialState` of the primary
     * constructor
     *
     * @param dropOnSame [Boolean]
     * Default value is inherited from [ViewStateStoreConfig.dropOnSame]
     *
     * @see ViewStateStore primary constructor
     */
    constructor(initialData: V, dropOnSame: Boolean = ViewStateStoreConfig.dropOnSame) :
        this(initialState = ViewState(initialData), dropOnSame = dropOnSame)

    /** Empty companion object for extensions purpose */
    companion object
}
