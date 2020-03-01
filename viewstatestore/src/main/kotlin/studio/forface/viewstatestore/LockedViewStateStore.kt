@file:Suppress("MemberVisibilityCanBePrivate")

package studio.forface.viewstatestore

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.isActive
import studio.forface.viewstatestore.ViewState.*

/**
 * This class will store and handle the [ViewState] and submit it via a [LiveData].
 * `ViewStateStore` will only deliver the last [_data] when the observer become active.
 * This `ViewStateStore` is *locked* so `set` functions and `post` functions can only be called within [ViewStateStoreScope]
 *
 *
 * ### Publish:
 *
 * Use [ViewStateStoreScope.setState] for deliver the [ViewState] on the current thread.
 * Use [ViewStateStoreScope.postState] for deliver the [ViewState] on the main thread.
 * Use [state] for retrieve the last state.
 *
 *
 * ### Observe:
 *
 * Use [observe] for observe the published [ViewState] within a [LifecycleOwner]
 * Use [observeForever] for observe the published [ViewState]. NOTE! This will probably lead to a memory leak.
 *
 * Use [observeData] for observe ONLY the the published [_data] within a [LifecycleOwner]
 * Use [observeForever] for observe ONLY the the published [_data]. NOTE! This will probably lead to a memory leak.
 *
 * @see ViewStateObserver
 *
 *
 * ### Get:
 *
 * Use [state] for get the current nullable [ViewState]
 * Use [unsafeData] for get the current [ViewState]
 * @throws KotlinNullPointerException if no [ViewState] is available
 *
 * Use [_data] for get the current nullable [V]
 * Use [unsafeData] for get the current [V]
 * @throws KotlinNullPointerException if no [ViewState.data] is available
 *
 *
 * ### Await:
 *
 * Use [await] for get [ViewState] as soon as available, through a `suspend` function
 * Use [awaitNext] for get next published [ViewState], through a `suspend` function
 *
 * Use [awaitData] for get [V] as soon as available, through a `suspend` function
 * Use [awaitNextData] for get next published [V], through a `suspend` function
 *
 *
 * ### iterator:
 *
 * Use `` for (viewState in viewStateStore) { ... } `` for get all the [ViewState] published in
 * this [ViewStateStore]
 *
 * Use `` for (data in viewStateStore.data) { ... } `` for get all the [V] published in this
 * [ViewStateStore]
 *
 * Use `` for ((data, error, loadingChange)) in viewStateStore.composed) { ... } `` for get all the
 * [ViewState] as [ComposedViewState] published in this [ViewStateStore]
 *
 *
 * --- --- --- --- ---
 *
 * This class is abstract and will be inherited from [ViewStateStore], that implements [ViewStateStoreScope] for
 * being able to call `set` functions and `post` functions without defining a [ViewStateStoreScope].
 *
 * @param V is the type of the [_data]
 *
 * @param dropOnSame This [Boolean] defines whether a publishing should be dropped if the same [ViewState] is already
 * the last [state]
 * Default value is inherited from [ViewStateStoreConfig.dropOnSame]
 * @see ViewStateStoreConfig.dropOnSame
 *
 *
 * @author Davide Giuseppe Farella
 */
abstract class LockedViewStateStore<V>(internal val dropOnSame: Boolean) {

    // region observe
    /**
     * @see LiveData.observe with an [Observer] created with an instance of [ViewStateObserver]
     * @param block a lambda with [ViewStateObserver] as receiver that properly sets callbacks on it.
     */
    inline fun observe(owner: LifecycleOwner, block: ViewStateObserver<V>.() -> Unit) {
        val observer = `access$onCreateViewStateObserver`(owner)
        observer.block()
        liveData.observe(owner, observerWith(observer))
    }

    /**
     * Observe the [ViewStateStore] and trigger [block] only on [ViewStateObserver.onData]
     * @see LiveData.observe with an [Observer] created with an instance of [ViewStateObserver]
     * @param block a lambda with [ViewStateObserver] as receiver that properly sets callbacks on it.
     */
    inline fun observeData(owner: LifecycleOwner, crossinline block: (V) -> Unit) {
        val observer = `access$onCreateViewStateObserver`(owner)
        observer.onData = { block(it) }
        liveData.observe(owner, observerWith(observer))
    }

    /**
     * @see LiveData.observeForever with an [Observer] created with an instance of [ViewStateObserver]
     * @param block a lambda with [ViewStateObserver] as receiver that properly sets callbacks on it.
     */
    inline fun observeForever(block: ViewStateObserver<V>.() -> Unit) {
        val observer = `access$onCreateViewStateObserver`()
        observer.block()
        liveData.observeForever(observerWith(observer))
    }

    /**
     * Observe the [ViewStateStore] and trigger [block] only on [ViewStateObserver.onData]
     * @see LiveData.observeForever with an [Observer] created with an instance of [ViewStateObserver]
     * @param block a lambda with [ViewStateObserver] as receiver that properly sets callbacks on it.
     */
    inline fun observeDataForever(crossinline block: (V) -> Unit) {
        val observer = `access$onCreateViewStateObserver`()
        observer.onData = { block(it) }
        liveData.observeForever(observerWith(observer))
    }
    // endregion

    // region `in`
    /**
     * @return [SuspendIterator] of [ViewState] for get all [ViewState] published in this
     * [ViewStateStore]
     */
    suspend operator fun iterator(): SuspendIterator<ViewState<V>> {
        var isEmpty = true
        return object : SuspendIterator<ViewState<V>> {

            override suspend fun hasNext() = coroutineScope { isActive }

            override suspend fun next(): ViewState<V> {
                return if (isEmpty) {
                    isEmpty = false
                    await()
                } else {
                    awaitNext()
                }
            }
        }
    }

    /** @return [SuspendIterator] of [V] for get all [V] published in this [ViewStateStore] */
    val data get() = DataIterable(this)

    class DataIterable<V>(val store: LockedViewStateStore<V>) {
        suspend operator fun iterator(): SuspendIterator<V> = with(store) {
            var isEmpty = true
            return object : SuspendIterator<V> {

                override suspend fun hasNext() = coroutineScope { isActive }

                override suspend fun next(): V {
                    return if (isEmpty) {
                        isEmpty = false
                        awaitData()
                    } else {
                        awaitNextData()
                    }
                }
            }
        }
    }

    /**
     * @return [SuspendIterator] of [ComposedViewState] for get all [ViewState] as
     * [ComposedViewState] published in this [ViewStateStore]
     */
    val composed get() = ComposedViewStateIterable(this)

    class ComposedViewStateIterable<V>(val store: LockedViewStateStore<V>) {
        suspend operator fun iterator(): SuspendIterator<ComposedViewState<V>> = with(store) {
            var isEmpty = true
            return object : SuspendIterator<ComposedViewState<V>> {

                override suspend fun hasNext() = coroutineScope { isActive }

                override suspend fun next(): ComposedViewState<V> {
                    return if (isEmpty) {
                        isEmpty = false
                        await().asComposed()
                    } else {
                        awaitNext().asComposed()
                    }
                }
            }
        }
    }
    // endregion

    // region get
    /** @return (OPTIONAL) current [ViewState] of the */
    fun state() = liveData.value

    /**
     * @return current [ViewState] asserted as non null
     * @throws KotlinNullPointerException if [state] is null.
     */
    fun unsafeState() = state()!!

    /** @return (OPTIONAL) [V] from current [ViewState.data] */
    fun data() = state()?.data

    /**
     * @return [V] current [ViewState.data] asserted as non null
     * @throws KotlinNullPointerException if [state] is null.
     */
    fun unsafeData() = unsafeState().data!!
    // endregion

    // region await
    /** @return [ViewState] as soon as available */
    @Suppress("RedundantSuspendModifier")
    suspend fun await() = coroutineScope {
        var s = state()
        while (s == null) {
            ensureActive()
            s = state()
        }
        s
    }

    /** @return next published [ViewState] */
    @Suppress("RedundantSuspendModifier")
    suspend fun awaitNext() = coroutineScope {
        val old = state()
        var s = old
        while (s == null || s == old) {
            ensureActive()
            s = state()
        }
        s
    }

    /** @return [V] as soon as available */
    @Suppress("RedundantSuspendModifier")
    suspend fun awaitData() = coroutineScope {
        var d = data()
        while (d == null) {
            ensureActive()
            d = data()
        }
        d
    }

    /** @return next published [V] */
    @Suppress("RedundantSuspendModifier")
    suspend fun awaitNextData() = coroutineScope {
        val old = data()
        var d = old
        while (d == null || d == old) {
            ensureActive()
            d = data()
        }
        d
    }
    // endregion


    /**
     * This property will store the last available [ViewState.data], so it will be emitted every
     * time. It is useful if, for instance, we have a failure after rotating the screen: in
     * that case the data would not be emitter.
     */
    private var _data: V? = null

    /**
     * This [List] will store the instance of the [ViewState], for
     * [ViewState.Success.singleEvent], that has already been published, for do not publish them
     * again
     */
    private val singleEvents = mutableListOf<Success<V>>()

    /**
     * This property will store the last [ViewState.Error], so it won't be delivered every
     * time for every new [Observer] so, if for instance we rotate the screen, the last
     * error won't be delivered again.
     */
    private var lastError: Error? = null

    /** An instance of [MutableLiveData] of [ViewState] of [V] for dispatch [ViewState]'s */
    @PublishedApi
    internal open val liveData = MutableLiveData<ViewState<V>>()

    /**
     * @return an instance of [Observer]
     * @see handleViewState
     */
    @PublishedApi
    internal fun observerWith(observer: ViewStateObserver<V>) =
            Observer<ViewState<V>> { viewState -> handleViewState(observer, viewState) }

    /**
     * Handle the delivery of a [ViewState] though [ViewStateObserver]
     *
     * This will save [ViewState.data] in case of success and then trigger the callbacks the following callbacks on the
     * given [ViewStateObserver]: [ViewStateObserver.onEach], [ViewStateObserver.onData], [ViewStateObserver.onError]
     * and [ViewStateObserver.onLoadingChange].
     */
    protected fun handleViewState(
            observer: ViewStateObserver<V>,
            viewState: ViewState<V>
    ) = with(observer) {
        // Deliver every ViewState.
        onEach(viewState)

        // Every time the observer is triggered for any reason ( loading change, data or error ),
        // if ViewState is Success, we store the new data then, in every case, if data is not
        // null, we deliver the data.
        if (viewState is Success) {
            // Check if there is any instance of current data in `singleEventData`
            _data = if (singleEvents.any { it === viewState }) null else viewState.data

            // If ViewState is single event and data has already been published, add it to
            // `singleEventData`, so it won't be published again
            if (viewState.singleEvent && liveData.hasActiveObservers())
                singleEvents += viewState
        }
        _data?.let(onData)

        // Every time the observer is triggered for any reason ( loading change, data or error ),
        // we instantiate a new NULL ViewState.Error on newError then, if ViewState is Error
        // we store the value in newError then, if error is different from lastError, we
        // deliver it if not Null and store in lastError.
        val newError = viewState as? Error
        if (newError !== lastError) {
            newError?.let(onError)
            lastError = newError
        }

        // If View.Stare is a loading change, we deliver it.
        onLoadingChange(viewState is Loading)
    }

    /** @return a new instance of [ViewStateObserver] */
    protected open fun onCreateViewStateObserver(owner: LifecycleOwner? = null) =
            ViewStateObserver<V>()

    @PublishedApi
    @Suppress("FunctionName")
    internal fun `access$onCreateViewStateObserver`(owner: LifecycleOwner? = null) =
            onCreateViewStateObserver(owner)
}
